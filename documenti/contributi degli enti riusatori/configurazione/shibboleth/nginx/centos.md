# Installazione NGINX + shibbolet on Centos7

Riferimento alla guida https://github.com/HarrisonSong/shibboleth_setup_with_nginx_on_ubuntu

```bash


(
#installati in test
1. yum install memcached
2. yum install libmemcached
3. libodbc1
	yum install unixODBC
)

1.
/etc/yum.repos.d/
creato vim shibboleth.repo
con 

[security_shibboleth]
name=Shibboleth (CentOS_7)
baseurl=http://repo.ecosis.csi.it/artifactory/rpm-shibboleth-csi/CentOS_7/
gpgcheck=0
enabled=1
proxy=_none_


3.
 yum install fcgi-devel
 
4.
yum install supervisor

5.
/etc/supervisord.d

6.
yum install make rpm-build rpmdevtools yum-utils rsync


7.
download
https://github.com/nginx-shib/shibboleth-fastcgi/blob/master/shibboleth-rebuild.sh

8.
cd /opt/shibboleth-fastcgi
vim shibboleth-rebuild.sh
#!/bin/sh
set -e

# Handle different package manager changes
#if [ -x "$(command -v dnf)" ]; then
#    pkgmanager="dnf"
#    download_cmd="dnf download"
#    builddep_cmd="dnf builddep"
#
#    # For build dependencies: doxygen
#    dnf config-manager --enable powertools
#else
	pkgmanager="yum"
	download_cmd="yumdownloader"
	builddep_cmd="yum-builddep"
#fi

# EL6 requires specific packages
os_version=$(rpm -qa --queryformat '%{VERSION}' '(redhat|sl|slf|centos|oraclelinux)-release(|-server|-workstation|-client|-computenode)')
case $os_version in
	6*)
	builddep_pkgs="httpd-devel"
	;;
esac

# Download specific Shibboleth version or just the latest version
if [ "$_SHIBBOLETH_VERSION" ]; then
	$download_cmd --source "shibboleth-$_SHIBBOLETH_VERSION"
else
	$download_cmd --source shibboleth
fi

# Install the SRPM's dependencies
sudo $builddep_cmd -y shibboleth*.src.rpm

# At time of writing (Sep 2020) there is no way of engaging conditional flags
# with dnf builddep (or yum-builddep) so the optional PreReq packages need to
# be manually installed Info is scarce on this topic but see
# https://github.com/ceph/ceph/pull/8016
sudo $pkgmanager install -y \
	fcgi-devel \
	$builddep_pkgs

# Rebuild with FastCGI support
rpmbuild --rebuild shibboleth*.src.rpm --with fastcgi

# Remove original SRPM
rm shibboleth*.src.rpm -f

9.
_SHIBBOLETH_VERSION=3.2.0-2.1 sh shibboleth-rebuild.sh

10.
cd /root/rpmbuild/RPMS/x86_64
rpm -i shibboleth-3.2.0-2.1.x86_64.rpm


11.
yum install nginx

12.
/etc/nginx

vim nginx.conf


13.
wget https://nginx.org/download/nginx-1.20.1.tar.gz
tar -xzvf nginx-1.20.1.tar.gz

download zip https://github.com/nginx-shib/nginx-http-shibboleth
unzip nginx-http-shibboleth-master.zip

cd nginx-1.20.1/
./configure --with-compat --add-dynamic-module=../nginx-http-shibboleth-master
make modules

	mkdir /etc/nginx/modules
	cp objs/ngx_http_shibboleth_module.so /etc/nginx/modules/


download zip https://github.com/openresty/headers-more-nginx-module
unzip headers-more-nginx-module-master.zip

cd nginx-1.20.1/
./configure --with-compat --add-dynamic-module=../headers-more-nginx-module-master --add-dynamic-module=../nginx-http-shibboleth-master
make modules

cp objs/*.so /usr/share/nginx/modules/
cd /usr/share/nginx/modules/
ll
cp ngx_http_shibboleth_module.conf ngx_http_headers_more_filter_module.conf
vi ngx_http_headers_more_filter_module.conf

14.
cd /etc/supervisord.d/
vi shibboleth.ini


[fcgi-program:shibauthorizer]
command=/usr/lib64/shibboleth/shibauthorizer
socket=unix:///var/run/shibboleth/shibauthorizer.sock
socket_owner=shibd:shibd
socket_mode=0666
user=shibd
stdout_logfile=/var/log/supervisor/shibauthorizer.log
stderr_logfile=/var/log/supervisor/shibauthorizer.error.log

[fcgi-program:shibresponder]
command=/usr/lib64/shibboleth/shibresponder
socket=unix:///var/run/shibboleth/shibresponder.sock
socket_owner=shibd:shibd
socket_mode=0666
user=shibd
stdout_logfile=/var/log/supervisor/shibresponder.log
stderr_logfile=/var/log/supervisor/shibresponder.error.log



15.
systemctl restart shibd
systemctl restart supervisord
systemctl restart nginx
----------------

preso shibboleth2.xml da ambiente di test di CSI attuale
-modificato file per nuvo ambiente


N.B
Il file che c'è dentro metadata di Shibboleth non è quello utilizzato a runtime, quindi bisogna fare attenzione che sia quello effettivo che si trova dentro /etc/shibbolet/certs almeno x509


----------------------------------------------------



6.
posizionarsi in /opt
git clone https://github.com/nginx-shib/shibboleth-fastcgi.git



.
vim shib.ini

[fcgi-program:shibauthorizer]
command=/usr/lib64/shibboleth/shibauthorizer
socket=unix:///var/run/shibboleth/shibauthorizer.sock
; socket_owner=_shibd:_shibd
; socket_owner=root:root
socket_owner=ec2-instance-connect:libstoragemgmt
socket_mode=0666
; user=_shibd
user = root
stdout_logfile=/var/log/supervisor/shibauthorizer.log
stderr_logfile=/var/log/supervisor/shibauthorizer.error.log
[fcgi-program:shibresponder]
command=/usr/lib64/shibboleth/shibresponder
socket=unix:///var/run/shibboleth/shibresponder.sock
; socket_owner=_shibd:_shibd
; socket_owner=root:root
socket_owner=ec2-instance-connect:libstoragemgmt
socket_mode=0666
; user=_shibd
user = root
stdout_logfile=/var/log/supervisor/shibresponder.log
stderr_logfile=/var/log/supervisor/shibresponder.error.log

```