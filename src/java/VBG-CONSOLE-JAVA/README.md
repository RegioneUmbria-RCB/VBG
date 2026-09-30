# Introduzione

Il progetto dei servizi di backend della console JAVA

# Installazione Eclipse

Scaricare i sorgenti da GIT come general project.
Una volta scaricati vanno creati tre progetti mediante il comando:

> File > open project fom filesystem

Aprire i tre progetti in ordine:
- smart-core
- smart-console
- smart-servizi-console

Configurare la user library **smart-core-lib** con le librerie prese da smart-core\lib

Per i tre progetti nel build path va aggiunta la jdk 1.6.0_45 e il tomcat6 come dipendenza.

Nei progetti **smart-console** e **smart-servizi-console** va aggiunta la **smart-core-lib** anche nel deployment assembly.

Nei project facets settare la jdk alla 1.6 e nel compiler la compliance alla 1.6
