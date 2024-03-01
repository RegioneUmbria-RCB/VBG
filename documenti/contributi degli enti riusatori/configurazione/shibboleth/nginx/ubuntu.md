# Installazione in UBUNTU

## Compile Nginx with Shibboleth module

guida presa da https://github.com/nginx-shib/nginx-http-shibboleth/blob/master/CONFIG.rst

### Step 1: Obtain the NGINX Open Source Release

Scaricare la lista aggiornata dei pacchetti e delle nuove versioni disponibili nei repository

```shell 
$ sudo apt-get update
$ sudo apt-get install git
```

Determine the NGINX Open Source version that corresponds to your NGINX Plus installation. In this example, it's NGINX 1.11.5.
```shell
$ nginx -v
nginx version: nginx/1.18.0 (Ubuntu)
```

Download the corresponding NGINX Open Source package at nginx.org/download:

```shell
$ wget https://nginx.org/download/nginx-1.18.0.tar.gz
$ tar -xzvf nginx-1.18.0.tar.gz
```
### Step 2: Obtain the Module Sources

Obtain the source for the **nginx-http-shibboleth** NGINX module from GitHub:

```shell
$ git clone https://github.com/nginx-shib/nginx-http-shibboleth.git
```


### Step 3: Compile the Dynamic Module

Compile the module by first running the configure script with the --with-compat argument, which creates a standard build environment supported by both NGINX Open Source and NGINX Plus. Then run make modules to build the module:

```shell
$ cd nginx-1.18.0/

 ./configure --with-compat --add-dynamic-module=/root/operazioni/nginx-http-shibboleth

```


Se si verifica: 
Errore ubuntu ./configure: error: C compiler cc is not found

> sudo apt-get install build-essential

Se si verifica
**./configure: 13: /root/operazioni/nginx-http-shibboleth/config: Syntax error: end of file unexpected (expecting "then")**

> sudo apt-get install dos2unix

> dos2unix root/operazioni/nginx-http-shibboleth/config

Se si verifica 
**./configure: error: the HTTP rewrite module requires the PCRE library.**

> sudo apt-get install libpcre3-dev


Se si verifica 
**./configure: error: the HTTP gzip module requires the zlib library.**

> sudo apt-get install zlib1g-dev


## installare headers-more-nginx-module-0.32.zip

```shell
$ sudo wget https://github.com/openresty/headers-more-nginx-module/archive/v0.35.zip

$ sudo unzip headers-more-nginx-module-0.35.zip

$ sudo ./configure --with-compat --add-dynamic-module=/root/operazioni/headers-more-nginx-module-0.35 --add-dynamic-module=/root/operazioni/nginx-http-shibboleth

$ sudo make modules
```


### Copy the module library (.so file) to /etc/nginx/modules:


```shell
 $ sudo cp objs/ngx_http_shibboleth_module.so /usr/share/nginx/modules
 $ sudo cp objs/ngx_http_headers_more_filter_module.so /usr/share/nginx/modules/
```


### Step 4: Load and Use the Module

To load the module into NGINX Plus, add the load_module directive in the top‑level (main) context of your nginx.conf configuration file (not within the http or stream context):

> vim /etc/nginx/modules-available/mod_ngx_http_shibboleth.conf

inserire il contenuto
```shell
load_module modules/ngx_http_shibboleth_module.so;
```

Attivare il modulo su nginx 

```shell
cd /etc/nginx/modules-enabled/
ln -s /etc/nginx/modules-available/mod_ngx_http_shibboleth.conf 50-mod_ngx_http_shibboleth.conf
```


> vim /etc/nginx/modules-available/ngx_http_headers_more_filter_module.conf

inserire il contenuto
```shell
load_module modules/ngx_http_headers_more_filter_module.so;
```


> ln -s /etc/nginx/modules-available/ngx_http_headers_more_filter_module.conf 50-ngx_http_headers_more_filter_module.conf


> vim /etc/nginx/shib_clear_headers

inserire il contenuto
```shell
# Ensure that you add directives to clear input headers for *all* attributes
# that your backend application uses. This may also include variations on these
# headers, such as differing capitalisations and replacing hyphens with
# underscores etc -- it all depends on what your application is reading.
#
# Note that Nginx silently drops headers with underscores
# unless the non-default `underscores_in_headers` is enabled.

# Shib-* doesn't currently work because * isn't (yet) supported
more_clear_input_headers
    Auth-Type
    Shib-Application-Id
    Shib-Authentication-Instant
    Shib-Authentication-Method
    Shib-Authncontext-Class
    Shib-Identity-Provider
    Shib-Session-Id
    Shib-Session-Index
    Remote-User;

# more_clear_input_headers
#     EPPN
#     Affiliation
#     Unscoped-Affiliation
#     Entitlement
#     Targeted-Id
#     Persistent-Id
#     Transient-Name
#     Commonname
#     DisplayName
#     Email
#     OrganizationName;
```


## Installare Shibboleth SP


> sudo apt-get install shibboleth-sp-utils

> sudo apt-get install supervisor

> sudo touch /etc/supervisor/conf.d/shib.conf

Inserire il contenuto
```shell
[fcgi-program:shibauthorizer]
command=/usr/lib/x86_64-linux-gnu/shibboleth/shibauthorizer
socket=unix:///var/run/shibboleth/shibauthorizer.sock
socket_owner=_shibd:_shibd
socket_mode=0666
user=_shibd
stdout_logfile=/var/log/supervisor/shibauthorizer.log
stderr_logfile=/var/log/supervisor/shibauthorizer.error.log

[fcgi-program:shibresponder]
command=/usr/lib/x86_64-linux-gnu/shibboleth/shibresponder
socket=unix:///var/run/shibboleth/shibresponder.sock
socket_owner=_shibd:_shibd
socket_mode=0666
user=_shibd
stdout_logfile=/var/log/supervisor/shibresponder.log
stderr_logfile=/var/log/supervisor/shibresponder.error.log
```

> sudo service supervisor restart

## configurazione Shibboleth

### creazione dei certificati



>  openssl req -new -x509 -keyout rootCAkey.pem -out rootCAcert.pem -days 3650

**La passphrase dovrà essere inserita nel file shibboleth2.xml**

```shell
Generating a RSA private key
...........................................................................+++++
.....+++++
writing new private key to 'rootCAkey.pem'
Enter PEM pass phrase:
Verifying - Enter PEM pass phrase:
-----
You are about to be asked to enter information that will be incorporated
into your certificate request.
What you are about to enter is what is called a Distinguished Name or a DN.
There are quite a few fields but you can leave some blank
For some fields there will be a default value,
If you enter '.', the field will be left blank.
-----
Country Name (2 letter code) [AU]:IT
State or Province Name (full name) [Some-State]:PERUGIA
Locality Name (eg, city) []:PERUGIA
Organization Name (eg, company) [Internet Widgits Pty Ltd]:RETELIT
Organizational Unit Name (eg, section) []:PAABS SVILUPPO PAL
Common Name (e.g. server FQDN or YOUR name) []:servizidigitali.fo.cittametropolitana.torino.it
Email Address []:vbgsupport.paevo@retelit.it

```

> mkdir /etc/shibboleth/certs

> cp rootCA* /etc/shibboleth/certs

> chmod 644 /etc/shibboleth/certs/rootCAkey.pem

> cp metadata_federazione_gasp_cmto.xml /etc/shibboleth/metadata/


> mkdir /etc/shibboleth/conf/

> mv attribute-*.xml /etc/shibboleth/conf/

> mv shibboleth2.xml /etc/shibboleth/shibboleth2.xml



### configurazione NGINX

Nella configurazione del site da proteggere inserire le righe per indicare i PATH protetti da shibauthorizer/responder

Il path è quello definito nell'attributo **handlerURL** del tag XML

```xml

<ApplicationOverride id="APPLICATION_SITE_DA_PROTEGGERE_443_LIV1_SPID"
					entityID="SP_SITE_DA_PROTEGGERE_443_LIV1_SPID">
			<Sessions lifetime="28800" 
                timeout="3600"
                 checkAddress="false"
			handlerURL="https://mio.servizio.it/SITE_DA_PROTEGGERE_443_LIV1_SPID/Shibboleth.sso"			
            handlerSSL="true"
            idpHistory="false" idpHistoryDays="7" 
            cookieName ="COLL-SITE_DA_PROTEGGERE_443_LIV1"
			cookieProps="; path=/; secure; HttpOnly" consistentAddress="false">

```
In questo caso `https://mio.servizio.it/SITE_DA_PROTEGGERE_443_LIV1_SPID`


Esempio: 
``` bash


        location /SITE_DA_PROTEGGERE_443_LIV1_SPID/shibauthorizer {
                internal;
                include fastcgi_params;
                fastcgi_pass unix:/var/run/shibboleth/shibauthorizer.sock;
        }

        location /SITE_DA_PROTEGGERE_443_LIV1_SPID/Shibboleth.sso {
                include fastcgi_params;
                fastcgi_pass unix:/var/run/shibboleth/shibresponder.sock;
        }

        location /SITE_DA_PROTEGGERE_443_LIV1_SPID/shibboleth-sp {
                alias /usr/share/shibboleth/;
        }

        location /ibcauthenticationgateway {
                include shib_clear_headers;
                #more_clear_input_headers 'displayName' 'mail' 'persistent-id';
                shib_request /SITE_DA_PROTEGGERE_443_LIV1_SPID/shibauthorizer;
                shib_request_use_headers on;

                proxy_pass http://ibcauthenticationgateway;
        }


```

### RIAVVIO DEI SERVIZI

```shell
service shibd restart ; supervisorctl restart shibauthorizer shibresponder ; service nginx restart
```

### create metadata da mettere in COT

Per generare i Metati da mettere in COT usare la URL 

`https://mio.servizio.it/SITE_DA_PROTEGGERE_443_LIV1_SPID/Shibboleth.sso/Metadata`

Il file va inviato ai gestori dell'identity provider
