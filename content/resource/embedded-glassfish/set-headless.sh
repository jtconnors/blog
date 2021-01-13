
memstats()
{
        OUTPUT=""
	fields="MemTotal MemFree Buffers Cached"
	for f in ${fields}
	do
		set - `grep ${f} /proc/meminfo`
		OUTPUT="${OUTPUT}${1} ${2} ${3} "
	done
	echo ${OUTPUT}
}

SCRIPTS="S99ttyusb0
S99tablet-browser-daemon
S99metalayer-crawler0
S99mediaplayer-daemon
S99hulda
S99alarmd
S70obexsrv
S61osso-systemui
S60af-base-apps
S57btcond
S56bluez-utils
S54libgpsbt
S54gpsdriver
S52hildon-application-manager
S51hildon-desktop
S50af-startup
S45maemo-launcher
S41product-code
S30ke-recv
S26osso-systemui-early
S26esd
S25multimediad
S24dsp-init
S22af-services
S21x-server
S21mce
S20osso-applet-display
S20hal
S20dbus
S12fb-progress.sh"

memstats

for s in ${SCRIPTS}
do
   /etc/rc2.d/${s} stop
   sleep 2
done

memstats
