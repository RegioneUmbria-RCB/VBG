rem keytool -delete -alias test -keypass testpass -keystore test-keystore.jks -storepass testpass
keytool -genkeypair -keyalg RSA -keysize 2048 -dname "cn=Comune di Test, ou=Area Sviluppo, o=Comune di Test, c=IT" -alias test -keypass testpass -keystore test-keystore.jks -storepass testpass -validity 10000
