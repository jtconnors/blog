#!/bin/sh
GNULINKS_DIR=/opt/gnulinks/bin
GNU_PROGS="ar as egrep grep m4 make nm objcopy objdump strings strip tar thumb"

mkdir -p ${GNULINKS_DIR}
cd ${GNULINKS_DIR}
for PROG in ${GNU_PROGS}
do
    ln -s /usr/sfw/bin/g${PROG} ${PROG}
done
