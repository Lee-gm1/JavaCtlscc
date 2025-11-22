!!
This program downloads the
BASIC! manual, De Re BASIC!

The manual is in PDF format
and can be read by any of the
PDF readers available on the
Google Play Store.

Most Android products come
with a PDF reader application.
This program will attempt to open
the manual for reading.

The manual will be downloaded
to the SDCARD as
"/sdcard/rfo-basic/data/De_Re_BASIC!.pdf"

This download will take up
to a minute. Be patient.
!!


file$ = "De_Re_BASIC!-v0191.pdf"

BYTE.OPEN r, fn, "https://laughton.com/basic/programs/apks/De_Re_BASIC%21-v0191.pdf"

POPUP "Be patient. Wait for 'Download Done' Message", 0, 0, 1
BYTE.COPY fn, file$

PRINT "Download Done."
PRINT "Opening the manual in your PDF reader."

? "/sdcard/rfo-basic/data/" + file$

! 251123Sun2A : Fixed...

END 



