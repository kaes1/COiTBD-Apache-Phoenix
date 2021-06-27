# COiTBD-Apache-Phoenix
Projekt na przedmiot COiTBD oparty o Apache Phoenix.

# Wymagania
- Java 1.8 i skonfigurowana zmienna środowiskowa JAVA_HOME wskazująca na folder z JDK.
- Zainstalowany Maven 

# Budowanie jar
W głównym katalogu projektu należy uruchomić `mvn clean install`. W przypadku sukcesu w folderze target powstanie plik jar.  

# Uruchomienie środowiska
Środowisko ze skonfigurowanymi rozszerzeniami:   
`docker run -d krzysztofsobocik/hbase-apache-phoenix-fuzzy-extensions`  

Środowisko bez rozszerzeń:     
`docker run -d boostport/hbase-phoenix-all-in-one. `  

