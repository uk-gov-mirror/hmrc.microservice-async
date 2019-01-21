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

import play.api.mvc.SessionCookieBaker
import uk.gov.hmrc.crypto.{Decrypter, Encrypter, _}
import akka.stream.Materializer
import play.api.Play
import play.api.Play.current
import uk.gov.hmrc.play.bootstrap.filters.frontend.crypto.SessionCookieCryptoFilter
import concurrent.ExecutionContext.Implicits.global

import scala.concurrent.ExecutionContext


object SessionCookieCryptoFilters extends SessionCookieCryptoFilter {

  // Lazy because the filter is instantiated before the config is loaded
  lazy val applicationCrypto = new ApplicationCrypto(Play.current.configuration.underlying)
  private lazy val crypto = applicationCrypto.SessionCookieCrypto

  override protected val encrypter: Encrypter = encrypt
  override protected val decrypter: Decrypter = decrypt

  def encrypt: Encrypter = new Encrypter {
    override def encrypt(plain: PlainContent): Crypted = crypto.encrypt(plain)
  }

  def decrypt: Decrypter = new Decrypter {
    override def decrypt(reversiblyEncrypted: Crypted): PlainText = crypto.decrypt(reversiblyEncrypted)

    override def decryptAsBytes(reversiblyEncrypted: Crypted): PlainBytes = crypto.decryptAsBytes(Crypted(reversiblyEncrypted))
  }

  override implicit protected def ec: ExecutionContext = global

  override protected def sessionBaker: SessionCookieBaker = ???

  override implicit def mat: Materializer = Play.materializer
}
