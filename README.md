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

#### Template
Define Yaml files, and follow the test case.  
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

Support noop key. In the following sample, `${document}` should be generated as a JSON body.  
```yaml
name: index-dyn
method: PUT
endpoint: /${index}/${type}/${document.id}
body: 
  ${@noop}: ${document}
```

Support bulk command. Note the key must start with `@`, and they are ordered by string sorting.  
In current Elastic Search(5.1.1), it is a JSON object per bulk line.  
`"` is optional for json attribute key and value.
```yaml
ame: bulk
method: POST
endpoint: /_bulk
body: 
  ${@1}: {index: {_index: twitter, _type: tweet, _id: 300 }}
  ${@2}: {user: bulk-one, message: From Bulk Command}
  ${@3}: {"index": {"_index": "twitter", "_type": "tweet", "_id": "301" }}
  ${@4}: {"user": "bulk-two", "message": "From Bulk Command"}
```

#### Context, Loader and Template
There is a context `CommandTemplateContext`, and a loader `CommandTemplateLoader`.  
A simple context implementation `SimpleCommandTemplateContext` to support loading templates from file system or class path, also supports context hierarchy.  
A Yaml loader implementation `YmlCommandTemplateLoader` which using SnakeYaml to load templates.  
Template contains:  
* `name`, global unique key to find the template,  
* `method`, Http Method, mostly is `GET`, `PUT`, `DELETE` or `POST`. According to the document of Elastic Seach and which endpoint used,  
* `endpoint`, relative location. eg. `/_bulk`, `/${index}/${type}/${document.id}`.
* `body`, request body. Nested map.
* `params`, query string, One level map.
* `headers`, Http headers. One level map.

`endpoint`, `body`, `params` and `headers` can contains dynamic patterns. `Dynamic Patterns` is wrapped by `${}`, eg. `${document}` means get value of `document` property.  
To fetch value from given `params`, the OGNL library is used, which means format of dynamic pattern must follow the OGNL rules.
See test case [TestYmlLoader](https://github.com/nnest/sparrow/blob/master/sparrow-simple/src/test/java/com/github/nnest/sparrow/simple/TestYmlLoader.java) to find more information.

#### Executor
To execute the command, there is a `CommandExecutor`, and default implementation `DefaultCommandExecutor`.  
Executor finds values from parameter `params` into template, and transforms to request body, sends to Elastic Search server, and calls response listener to handle repsonse.  
Executor must have its context, rest client builder and body value converter.  
* Prepare templates via context,  
* Configure the rest client settings via rest client builder,  
* Any body value should be converted to string via `BodyValueConverter`, and there are 
  * `PrimitiveBodyValueConverter`,
  * `JacksonBodyValueConverter`, use FastXML Jackson object mapper to serializing, is default converter, 
  * `BodyValueConverterChain`, chain of converters.

#### Response Handler
To handle the response, implements the `CommandExecutionHandler`. No matter how the request sending, sync or async, always use execution handler to handle response. There are 
* `NoopCommandExecutionHandler`, actually do nothing, just for quickly implementation,
* `AbstractJacksonCommandExecutionHandler`, using FastXML Jackson object mapper to deserializing.

Reason of no standard response handler implementation is, cannot find the standard format(s). For specified rest request, the response format is predictable, but because Elastic Search supports very complex request formats, makes response unpredicatable. eg.  
* bulk request, the repsonse only depends on the request itself.   
* in some case, throw exception when not found, such as update. but in some case, response returned, such as exists.
So read document of Elastic Search, implements your own response handlers to handle the requests.

# License
MIT

# Seeking for Cooperator
If you are interested in this project, contact me via bradwoo8621@gmail.com.
