/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.msasync.filter

import org.mockito.ArgumentCaptor
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, Suite, WordSpecLike}
import play.api.Play
import play.api.http.HeaderNames
import play.api.mvc._
import play.api.test.{FakeApplication, FakeRequest}
import uk.gov.hmrc.crypto.{ApplicationCrypto, Crypted, PlainText}
import uk.gov.hmrc.play.test.WithFakeApplication

import scala.concurrent.Future

trait FakePlayApplication extends WithFakeApplication {
  this: Suite =>

  override lazy val fakeApplication = FakeApplication()
}

class SessionCookieCryptoFilterSpec extends WordSpecLike with Matchers with MockitoSugar with ScalaFutures with WithFakeApplication {

  val appConfig = Map("cookie.encryption.key" -> "MTIzNDU2Nzg5MDEyMzQ1Cg==")
  override lazy val fakeApplication = FakeApplication(additionalConfiguration = appConfig)

  val action: RequestHeader => Future[Result] = {
    val mockAction = mock[RequestHeader => Future[Result]]
    val outgoingResponse = Future.successful(Results.Ok.withCookies(Cookie(Session.COOKIE_NAME, "cookie")))
    when(mockAction.apply(any())).thenReturn(outgoingResponse)
    mockAction
  }

  def requestPassedToAction: RequestHeader = {
    val updatedRequest = ArgumentCaptor.forClass(classOf[RequestHeader])
    verify(action).apply(updatedRequest.capture())
    updatedRequest.getValue
  }

  "SessionCookieCryptoFilter" should {
    lazy val applicationCrypto = new ApplicationCrypto(Play.current.configuration.underlying)

    def createEncryptedCookie(cookieVal: String) = Cookie(Session.COOKIE_NAME, applicationCrypto.SessionCookieCrypto.encrypt(PlainText(cookieVal)).value)

    "decrypt the session cookie on the way in and encrypt it again on the way back" in {

      val encryptedIncomingCookie = createEncryptedCookie("cookie")
      val unencryptedIncomingCookie = Cookie(Session.COOKIE_NAME, "cookie")

      val incomingRequest = FakeRequest().withCookies(encryptedIncomingCookie)
      val response: Result = SessionCookieCryptoFilters(action)(incomingRequest).futureValue

      requestPassedToAction.cookies(Session.COOKIE_NAME) shouldBe unencryptedIncomingCookie

      val encryptedCookieResponse = Cookies.decodeSetCookieHeader(response.header.headers(HeaderNames.SET_COOKIE)).head.value
      applicationCrypto.SessionCookieCrypto.decrypt(Crypted(encryptedCookieResponse)).value shouldBe "cookie"
    }

  }

}
