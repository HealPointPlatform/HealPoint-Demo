# copyright (C) 2012 jc.unternet.net
SOURCEDIR := com/jcomeau/imagetagging
MAKEDIR = $(PWD)
PACKAGE := $(subst /,.,$(SOURCEDIR))
APPLICATION := TaggingApplet
SOURCES := $(wildcard $(SOURCEDIR)/*.java)
OBJECTS := $(SOURCES:.java=.class)
#JAVAC := /usr/lib/jvm/java-1.5.0-gcj-4.6/bin/javac -source 1.5 -Xlint
JAVAC := /usr/lib/jvm/java-6-sun-1.6.0.26/bin/javac -source 1.5 -Xlint
#JAVAC := javac -Xlint
#JAVA := /usr/lib/jvm/java-1.5.0-gcj-4.6/bin/java
JAVA := /usr/lib/jvm/java-6-sun-1.6.0.26/bin/java
#JAVA := java
#JAR := /usr/bin/jar
JAR := /usr/lib/jvm/java-6-sun-1.6.0.26/bin/jar
#JAR := jar
MAINCLASS := $(PACKAGE).$(APPLICATION)
JARFILE := $(APPLICATION).jar
IMAGES := $(wildcard *.jpg *.png)
SERVER := roiexpress.com
NEWSERVER := danboo.net
DB := roiexp_med
NEWDB := mysql4.loosefoot.com
USERNAME = $(shell awk '$$2 ~ /$(SERVER)/ {print $$4}' $(HOME)/.netrc)
NEWFTPSERVER := ftp.$(NEWSERVER)
NEWUSER := danboo
PASSWORD = $(shell awk '$$2 ~ /$(SERVER)/ {print $$6}' $(HOME)/.netrc)
DBUSER = $(shell awk '$$2 ~ /$(DB)/ {print $$4}' .netrc)
DBPASS = $(shell awk '$$2 ~ /$(DB)/ {print $$6}' .netrc)
NEWDBUSER = $(shell awk '$$2 ~ /$(NEWDB)/ {print $$4}' $(HOME)/.netrc)
NEWDBPASS = $(shell awk '$$2 ~ /$(NEWDB)/ {print $$6}' $(HOME)/.netrc)
export
set:
	set
timestamp.txt: .FORCE
	date > $@
%.class: %.java
	$(JAVAC) $(SOURCES)
%.jar: $(SOURCEDIR)/%.class $(OBJECTS) timestamp.txt
	jar cvfe $@ $(PACKAGE).$(APPLICATION) com org $(IMAGES) timestamp.txt
edit:
	vi $(SOURCES) Makefile
run:	$(JARFILE)
	$(JAVA) -jar $(JARFILE)
webrun: $(JARFILE)
	firefox http://tagging/
clean:
	rm -f mainclass.manifest $(JARFILE) $(OBJECTS)
manifest: $(JARFILE)
	cd /tmp && jar xf $(MAKEDIR)/$< META-INF/MANIFEST.MF && \
	 cat META-INF/MANIFEST.MF && \
	 rm -rf /tmp/META-INF
upload:
	cd .. && rsync -avuz $(DRYRUN) \
	 --exclude='.bzr*' \
	 $(notdir $(MAKEDIR)) \
	 www:/var/www/jcomeau/tmp/
connect: $(JARFILE)
	$(JAVA) $(PACKAGE).Connect
tar:
	rm -f /tmp/$(notdir $(MAKEDIR)).tbz
	cd .. && \
	 tar cvfj /tmp/$(notdir $(MAKEDIR)).tbz \
	  --exclude='$(notdir $(MAKEDIR))/job.txt' \
	  --exclude='$(notdir $(MAKEDIR))/.bzr*' \
	  --exclude='$(notdir $(MAKEDIR))/com/mysql*' \
	  --exclude='$(notdir $(MAKEDIR))/org*' \
	  --exclude='*.class' \
	  --exclude='*.jar' \
	  $(notdir $(MAKEDIR))
zip:
	rm -f /tmp/$(notdir $(MAKEDIR)).zip
	cd .. && \
	 zip -r \
	 /tmp/$(notdir $(MAKEDIR)) \
	 $(notdir $(MAKEDIR)) \
	 -x '$(notdir $(MAKEDIR))/job.txt' \
	 -x '$(notdir $(MAKEDIR))/.bzr*' \
	 -x '$(notdir $(MAKEDIR))/com/mysql*' \
	 -x '$(notdir $(MAKEDIR))/org*' \
	 -x '*.class' \
	 -x '*.jar'
dbinit:
	sudo mysql -u root < $(DB).sql
mysql:
	sudo mysql -u root $(DB)
%.sql: .FORCE
	sudo mysql -u root $(DB) < $@
dumpdb:
	sudo mysql -u root -e 'SELECT * FROM complaints;' $(DB)
localdb:
	echo 'query=select&table=complaints&where=patient_id+%3D+%2710%27&left_x=&top_y=&width=&height=&complaint=&tstamp=' \
	 | POST http://tagging/query.php
remotedb:
	echo 'query=select&table=complaints&where=patient_id+%3D+%2710%27&left_x=&top_y=&width=&height=&complaint=&tstamp=&ip=' \
	 | POST http://$(NEWSERVER)/med/design/query.php
remote:
	firefox http://$(NEWSERVER)/med/
log:
	tail -n 100 /var/log/apache2/error.log
access:
	tail -n 100 /var/log/apache2/access.log
server:
	java -cp $(APPLICATION).jar com.jcomeau.imagetagging.ConnectServer
client:
	java -cp $(APPLICATION).jar com.jcomeau.imagetagging.ConnectClient
test:
	firefox http://$(SERVER)/med/test.cgi
phpinfo:
	firefox http://$(SERVER)/med/test.php
phplocal:
	firefox http://tagging/test.php
admin:
	firefox https://$(SERVER):2083/
download: /mnt/fuse/ncftpgetrc
	ncftpget -f $</$(SERVER) -R oldsite /
	fusermount -u $<
ftp:
	ftp $(SERVER)  # credentials in ~/.netrc
sftp:
	sftp -v -v -v -P45667 $(NEWUSER)@$(NEWFTPSERVER)
%.net:
	ftp $@  # credentials in ~/.netrc
newupload: /mnt/fuse/ncftpgetrc
	cd oldsite && ncftpput -f $</$(NEWSERVER) -R / .
	fusermount -u $<
/mnt/fuse/ncftpgetrc: ~/src/ncftpgetrc.py
	$<
deploy: /mnt/fuse/ncftpgetrc *.html *.cgi *.jar *.php deploy_subdirs
	ncftpput -f $</$(SERVER) htm_dev \
	 $(filter-out $<, $?)
	ncftpput -u $(USERNAME) -p $(PASSWORD) \
	 -W 'chmod 755 test.cgi' \
	 $(SERVER) htm_dev .netrc
	touch $@
	fusermount -u $<
deploy_subdirs: /mnt/fuse/ncftpgetrc design/*page.html
	if [ "$(filter-out $<, $?)" ]; then \
	 ncftpput -f $</$(SERVER) htm_dev $(filter-out $<, $?); \
	fi
	touch $@
	fusermount -u $<
deploy_src: /mnt/fuse/ncftpgetrc com/jcomeau
	ncftpput -f $</$(SERVER) -R htm_dev/src_code/com $(filter-out $<, $?)
	fusermount -u $<
doctor: killjava
	firefox http://tagging/design/drpage.html
patient: killjava
	firefox http://tagging/design/patientpage.html
rdoctor: killjava
	firefox http://$(SERVER)/htm_dev/drpage.html
rpatient: killjava
	firefox http://$(SERVER)/htm_dev/patientpage.html
olddb:
	mysql -h $(SERVER) -u$(DBUSER) -p$(DBPASS) $(DB)
newdb:
	mysql -h $(NEWDB) -u$(NEWDBUSER) -p$(NEWDBPASS) roiexp_med
killjava:
	-killall java
drapp: $(APPLICATION).jar
	java -cp $< com.jcomeau.imagetagging.DoctorApplet
%.test: $(APPLICATION).jar com/jcomeau/imagetagging/%.class
	java -cp $< com.jcomeau.imagetagging.$*
%.edit: com/jcomeau/imagetagging/%.java
	vi $<
.FORCE:
cleandb:
	sudo mysql -u root -e \
	 'DELETE FROM complaints WHERE width IS NULL OR width=0;' $(DB)
bzrclean:
	bzr pack --clean-obsolete-packs
