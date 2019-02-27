ELK stack configuration:
All these three tools are based on JVM and before start installing them, please verify that
JDK has been properly configured. Check that standard JDK 1.8 installation,
JAVA_HOME ​ and Path


Elasticsearch   :https://www.elastic.co/downloads/elasticsearch
 	Windows     : =​ bin\elasticsearch.bat ​ 
 ​   Ubuntu      :​ ./elasticsearch ​ 
	default port:​ http://localhost:9200

Kibana          :https://www.elastic.co/downloads/kibana
    Windows     : = ​ bin\kibana.bat ​  ​ 
	Ubuntu      :​ ./kibana​ 
    default port:​ http://localhost:5601

Logstash        :https://www.elastic.co/downloads/logstash
1.Create one file ​ logstash.conf ​
Start logstash  :bin/logstash -f logstash.conf

Logstash Configuration:
input {
file {
type => ​ "java"
path => ​ "F:/Study/eclipse_workspace/elk-spring-boot/elk-example.log"
codec => multiline {
pattern => ​ "^%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME}.*"
negate => ​ "true"
}
}
what => ​ "previous"
}
filter {
if​​ [message] =~ ​ "\tat"​ { 
grok {
match => [​ "message"​ , ​ "^(\tat)"​ ]
}
}
add_tag => [​ "stacktrace"​ ]
grok {
match => [ ​ "message"​ ,
"(?<timestamp>%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME})
%{LOGLEVEL:level} %{NUMBER:pid} --- \[(?<thread>[A-Za-z0-9-]+)\]
[A-Za-z0-9.]*\.(?<class>[A-Za-z0-9#_]+)\s*:\s+(?<logmessage>.*)"​ ,
"message"​ ,
"(?<timestamp>%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME})
%{LOGLEVEL:level} %{NUMBER:pid} --- .+? :\s+(?<logmessage>.*)"
]
}
date {
}
match => [ ​ "timestamp"​ , ​ "yyyy-MM-dd HH:mm:ss.SSS"​ ]
}
output {
stdout {
}
codec => rubydebug
}
elasticsearch {
hosts => [​ "localhost:9200"​ ]
}
}

Kibana Configuration
	Before viewing the logs in Kibana, we need to configure the Index Patterns. We can configure ​ logstash-* ​ as default configuration. We can always change this index pattern in logstash side and configure in Kibana. For simplicity, we will work with default configuration.


