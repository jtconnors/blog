#!/bin/sh
GNULINKS_DIR=/opt/gnulinks
mkdir -p ${HOME}/GNU

for PROG in $*
do
    cd ${HOME}/GNU
    PREFIX=`echo ${PROG} | sed 's/-.*//'`
    wget ftp://ftp.gnu.org/gnu/${PREFIX}/${PROG}.tar.gz
    tar xzf ${PROG}.tar.gz
    cd ${PROG}
    ./configure --prefix=${GNULINKS_DIR}
    make
    make install
done
