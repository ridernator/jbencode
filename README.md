# jbencode
**Java library for parsing and creating bencoded files and strings**

It is based on the specification [here](https://wiki.theory.org/BitTorrentSpecification#Bencoding "Bencoding specification")

**Usage :**

###Parsing a bencoded string :###
Create a Bencoder object :
```java
Bencoder bencoder = new Bencoder();
```
The call the `read` method on a string :
```java
String test = "i20ei-30e6:ciaron5:rider";
List<BencodingElement> elements = bencoder.read(test);
```

