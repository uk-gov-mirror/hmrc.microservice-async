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

import play.api.Play
import uk.gov.hmrc.crypto.{ApplicationCrypto, Crypted, PlainText}
import uk.gov.hmrc.play.filters.MicroserviceFilterSupport
import uk.gov.hmrc.play.filters.frontend.CookieCryptoFilter

object SessionCookieCryptoFilters  extends CookieCryptoFilter with MicroserviceFilterSupport {

  // Lazy because the filter is instantiated before the config is loaded
  lazy val applicationCrypto = new ApplicationCrypto(Play.current.configuration.underlying)
  private lazy val crypto = applicationCrypto.SessionCookieCrypto

  override protected val encrypter: String => String = encrypt _
  override protected val decrypter: String => String = decrypt _

  def encrypt(plainCookie: String): String = crypto.encrypt(PlainText(plainCookie)).value

  def decrypt(encryptedCookie: String): String = crypto.decrypt(Crypted(encryptedCookie)).value
}
