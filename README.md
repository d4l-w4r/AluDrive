AluDrive
========

Google Drive client that uploads an encrypted version of your files

REQUIREMENTS:
=============
- Oracle Java 8
(Depending on your install it might be necessary to install the Java JCE from http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
and drag the two .jar files to {java-jre-home}/lib/security. 
Otherwise the cryptoTools.EncryptionBackend will throw java.security.InvalidKeyException)

The following .jars can be found at the specified locations in the dependencies.zip file in the repository. **Please make sure to place them all in your java build path**
- aludrive/crypt/*jbcrypt-0.3m.jar* 
- aludrive/libs/*commons-logging-1.1.1.jar*
- aludrive/libs/*google-api-client-1.18.0-rc.jar*
- aludrive/libs/*google-api-client-android-1.18.0-rc.jar*
- aludrive/libs/*google-api-client-appengine-1.18.0-rc.jar*
- aludrive/libs/*google-api-client-gson-1.18.0-rc.jar*
- aludrive/libs/*google-api-client-jackson2-1.18.0-rc.jar*
- aludrive/libs/*google-api-client-java6-1.18.0-rc.jar*
- aludrive/libs/*google-api-client-servlet-1.18.0-rc.jar*
- aludrive/libs/*google-http-client-1.18.0-rc.jar*
- aludrive/libs/*google-http-client-android-1.18.0-rc.jar*
- aludrive/libs/*google-http-client-appengine-1.18.0-rc.jar*
- aludrive/libs/*google-http-client-gson-1.18.0-rc.jar*
- aludrive/libs/*google-http-client-jackson2-1.18.0-rc.jar*
- aludrive/libs/*google-http-client-jdo-1.18.0-rc.jar*
- aludrive/libs/*google-oauth-client-1.18.0-rc.jar*
- aludrive/libs/*google-oauth-client-appengine-1.18.0-rc.jar*
- aludrive/libs/*google-oauth-client-java6-1.18.0-rc.jar*
- aludrive/libs/*google-oauth-client-jetty-1.18.0-rc.jar*
- aludrive/libs/*google-oauth-client-servlet-1.18.0-rc.jar*
- aludrive/libs/*gson-2.1.jar*
- aludrive/libs/*httpclient-4.0.1.jar*
- aludrive/libs/*httpcore-4.0.1.jar*
- aludrive/libs/*jackson-core-2.1.3.jar*
- aludrive/libs/*jdo2-api-2.3-eb.jar*
- aludrive/libs/*jetty-6.1.26.jar*
- aludrive/libs/*jetty-util-6.1.26.jar*
- aludrive/libs/*jsr305-1.3.9.jar*
- aludrive/libs/*transaction-api-1.1.jar*
- aludrive/*google-api-services-drive-v2-rev123-1.18.0-rc.jar*
