How to connect external library to project using maven

# Introduction #

Open project main .pom file. Inside the tag
```
<dependencies>
...
<\dependencies>
```

write

```
<dependency>
   <groupId>groupId of library</groupId>
   <artifactId>artifactId of library<artifactId>
   <version>version</version>
</dependency>
```

The same information should be added to .pom file of module, where we are using this library, but we needn't write tag `<version></version>`.

Example:  How to connect junit-4.10.

Main .pom file of project:

```
<dependencyManagement>
    <dependencies>
           
        <!--Some other dependencies-->           

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.10</version>
        </dependency>
			
    </dependencies>
</dependencyManagement>
```

Now .pom file of module, where we are using our library:

```
<dependencies>

    <!--Some other dependencies--> 

    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
    </dependency>

</dependencies>
```

Information about groupId, artifactId and version you may find on

http://mvnrepository.com/