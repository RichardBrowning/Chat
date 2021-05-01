J = java
JC = javac
JFLAGS = -g

all:
	$(JC) $(JFLAGS) src/*.java -d out/production

runCLient:
	$(J) out/production/ChatClient

runServer:
	$(J) out/production/ChatServer

clean:
	rm -f out/production/*.class

# JFLAGS = -g
# JC = javac
# #RM = rm -f

# .SUFFIXES: .java .class

# .java.class:
# 	$(JC) $(JFLAGS) $*.java

# CLASSES = \
#         server.java \
#         client.java \
#         user.java \

# default: classes

# classes: $(CLASSES:.java=.class)

# clean:
# 	#$(RM) *.class
# 	rm -f *.class