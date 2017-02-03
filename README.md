# Sparrow
An Elastic Search client.  

Sparrow is a sub module of NEST.

![](http://bradwoo8621.github.io/parrot/guide/img/nest.png)  


### Status
[![Build Status](https://travis-ci.org/nnest/sparrow.svg?branch=master)](https://travis-ci.org/nnest/sparrow) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/a6a0d538f7424fd7951585b1e157f169)](https://www.codacy.com/app/nnest/sparrow?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nnest/sparrow&amp;utm_campaign=Badge_Grade)

### Slack
[nest-group-buddies.slack.com](https://nest-group-buddies.slack.com/shared_invite/MTI0NjQzNTg0NzU2LTE0ODM3ODk2ODktMDczYTRkMDUzNQ)

# User Guide
### Sparrow-Simple
A very simple client for Elastic Search.  
Use Elastic Search 5.1.1, FastXML Jackson, OGNL, SnakeYaml.  

The default usage, see [TestYmlLoader](https://github.com/nnest/sparrow/blob/master/sparrow-simple/src/test/java/com/github/nnest/sparrow/simple/TestYmlLoader.java)  
Define Yaml files, and follow the test case. Implements your own response listener (not yet implemented, planning in 0.0.2)
```yaml
---
name: index-dynamic
method: PUT
endpoint: /${index}/${type}/${document.id}
params:
  timeout: 5m
  refresh: true
body:
  user: ${document.user}
  post_date: ${document.postDate}
  message: ${document.message}
  topic: ${document.topic}
headers:
  Authorization: Basic Idontknowwhatthehellitis!
```

# License
MIT

# Seeking for Cooperator
If you are interested in this project, contact me via bradwoo8621@gmail.com.
