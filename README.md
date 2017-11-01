Oracle JDK Download Links
=========================

**Note: By running this program you agree to the
[Oracle Binary Code License Agreement for Java SE](http://www.oracle.com/technetwork/java/javase/terms/license/index.html).**

## Dependencies

Linux, JDK 8+, Docker


## Introduction

The Oracle JDK Download Link tool creates a list of download links
for current JDK versions that are available on [Oracle's website](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

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

To get download links for JDK 9 execute

```bash
./get_download_links JDK9
```

The links are written to `stdout`. You can redirect the output to a file
for further processing.

Download using the generated links with `wget`, e.g.

```
wget --header \
  "Cookie: oraclelicense=accept-securebackup-cookie" \
  http://download.oracle.com/otn-pub/java/jdk/8u152-b16/aa0333dd3019491ca4f6ddbe78cdb6d0/jdk-8u152-linux-x64.tar.gz
```
