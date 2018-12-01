Oracle JDK Download Links
=========================

**Note: By running this program you agree to the
[Oracle Binary Code License Agreement for Java SE](https://www.oracle.com/technetwork/java/javase/terms/license/index.html).
or the [Oracle Technology Network License Agreement for Oracle Java SE](https://www.oracle.com/technetwork/java/javase/terms/license/javase-license.html)
respectively.**

## Requirements

Linux, JDK 8+, Docker with the
[selenium/standalone-chrome](https://github.com/SeleniumHQ/docker-selenium)
image.


## Introduction

The Oracle JDK Download Link tool creates a list of download links
for current JDK versions 8 and 11 that are available on [Oracle's website](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

The list can then be used to perform the actual download.


## Build

```bash
git clone https://github.com/veita/oracle-java-download.git
cd oracle-java-download
./gradlew assembleShadowDist
```


## Usage

To get download links for JDK 8 execute

```bash
./get_download_links JDK8
```

To get download links for JDK 11 execute

```bash
./get_download_links JDK11
```

The following options are available:

```
  --wd-url <URL>           the web driver URL, e.g. http://127.0.0.1:4444/wd/hub
  --proxy <PROXY>          optional proxy as host:port
  --proxy-user <USER>      user name for proxy authentication; currently
                           only basic authentication is supported
  --proxy-pass <PASSWORD>  password for proxy authentication
```

The links are written to `stdout`. You can redirect the output to a file
for further processing.

Download using the generated links with `wget`, e.g.

```
wget --header "Cookie: oraclelicense=accept-securebackup-cookie" \
  https://download.oracle.com/otn-pub/java/jdk/8u191-b12/2787e45523244c269598db4e85c52e0c/jdk-8u191-linux-x64.tar.gz

wget --header "Cookie: oraclelicense=accept-securebackup-cookie" \
  https://download.oracle.com/otn-pub/java/jdk/11.0.1+13/90cf5d8f370a4347a95050520eef3fb7/jdk-11.0.1_linux-x64_bin.tar.gz
```
