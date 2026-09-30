riferimenti in 
S:\AnalisiProgetti e Documenti\Regione Emilia Romagna\Integrazioni\fedERa\

passaggi:

1. nome del contesto:
	LoginUmbriaAG
	
keytool -importcert -alias loginumbriaprod -keystore /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/security/cacerts -file /home/init/regioneumbriait.crt	


>>> https://suape.regione.umbria.it/LoginUmbriaAG/

	WEB.XML

	<context-param>
		<param-name>icar.inf3.error.returnURL</param-name>
		<param-value>https://suape.regione.umbria.it/LoginUmbriaAG/</param-value>
	</context-param>

	<init-param>
		<param-name>spid</param-name>
		<param-value>https://suape.regione.umbria.it/LoginUmbriaAG/metadata</param-value>
	</init-param>

	METADATA.XML
		entityID="https://suape.regione.umbria.it/LoginUmbriaAG/metadata" 
		Location="https://suape.regione.umbria.it/LoginUmbriaAG/AssertionConsumerService" />


>>> https://suape.regione.umbria.it/Backend-LoginUmbriaAG/
	
	WEB.XML

	<context-param>
		<param-name>icar.inf3.error.returnURL</param-name>
		<param-value>https://suape.regione.umbria.it/Backend-LoginUmbriaAG/</param-value>
	</context-param>

	<init-param>
		<param-name>spid</param-name>
		<param-value>https://suape.regione.umbria.it/Backend-LoginUmbriaAG/metadata</param-value>
	</init-param>

	METADATA.XML
		entityID="https://suape.regione.umbria.it/Backend-LoginUmbriaAG/metadata" 
		Location="https://suape.regione.umbria.it/Backend-LoginUmbriaAG/AssertionConsumerService" />

>>> http://bandi.regione.umbria.it/LoginUmbriaAG/

	WEB.XML

	<context-param>
		<param-name>icar.inf3.error.returnURL</param-name>
		<param-value>http://bandi.regione.umbria.it/LoginUmbriaAG/</param-value>
	</context-param>

	<init-param>
		<param-name>spid</param-name>
		<param-value>http://bandi.regione.umbria.it/LoginUmbriaAG/metadata</param-value>
	</init-param>

	METADATA.XML
		entityID="http://bandi.regione.umbria.it/LoginUmbriaAG/metadata" 
		Location="http://bandi.regione.umbria.it/LoginUmbriaAG/AssertionConsumerService" />

>>>	http://pratiche.pa.umbria.it/LoginUmbriaAG/

	WEB.XML

	<context-param>
		<param-name>icar.inf3.error.returnURL</param-name>
		<param-value>http://pratiche.pa.umbria.it/LoginUmbriaAG/</param-value>
	</context-param>

	<init-param>
		<param-name>spid</param-name>
		<param-value>http://pratiche.pa.umbria.it/LoginUmbriaAG/metadata</param-value>
	</init-param>

	METADATA.XML
		entityID="http://pratiche.pa.umbria.it/LoginUmbriaAG/metadata" 
		Location="http://pratiche.pa.umbria.it/LoginUmbriaAG/AssertionConsumerService" />
