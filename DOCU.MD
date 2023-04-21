Encriptado para wrappers DEC()
mvn jasypt:encrypt -Djasypt.encryptor.password="ADMIN_PASS"

Desencriptado
mvn jasypt:decrypt -Djasypt.encryptor.password="the password"

Congif intellij VM options:
-Djasypt.encryptor.password="the password"
