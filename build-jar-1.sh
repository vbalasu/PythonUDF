mkdir build
(cd build; unzip -uo ../jython-standalone-2.7.1.jar)
(cd build; unzip -uo ../trifacta-base-udf.jar)
jar -cvf combined.jar -C build .
