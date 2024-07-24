[![License: MIT](https://img.shields.io/github/license/mashape/apistatus.svg)](LICENSE)

<p align="center"><img src="img/logo.png" alt="SYPApp" height="76" border="0" /></p>

# SYPApp (Scripting Your Process - Application)

**Scripting Your Process - Application**, or simply SYPApp, is a online software to support the business process-based interactive narrative design. It is a tool that helps screenwriters import BPMN files and associate BPMN's elements to narrative elements. The software generates step outlines, enables people to create narrative scenes, and exports narratives to script files or interactive narratives based on Inkle's scripting language.

## Features

- **List of narrative projects**: It is the  **SYPApp** start screen with all projects owned or in collaboration with the screenwriters. 
- **Start a narrative project**: Screenwriters can start a new narrative project, giving a project name and a description. 
- **Import a BPMN file**: When screenwriters import a BPMN file, the system automatically generates the step outlines.
- **Sentence adjustment**: Due to sentence auto-generation in step outlines, some of them could be weird. Thus, screenwriters can change their words and/or format to make them more understandable.
- **Characters creation**. A business process has many actors and they are translated into characters in narratives. This feature enables screenwriters to put information in characters that will show in the interactive narrative.
- **Scene organization**: It is the **SYPApp** main feature because it enables screenwriters to convert step outlines into narrative scenes. In other words, it is possible to add features such as characters, time, places, triggers, and dialogs into a step outline sentence.
- **Export to Interactive Narrative (Ink language)**: After importing the BPMN file and/or scenes organization screenwriters can export the narrative to the [Ink language](https://www.inklestudios.com/ink/) files. It allows people to run the narrative as a simulation and, check if it is equivalent to the BPMN file.
- **Export to Narrative Script**: After importing the BPMN file and/or scenes organization screenwriters can export the narrative into a narrative script file. That is, screenwriters can export the result to a textual format, easier to read than an ink's source code.

## Project status

**SYPApp** is still a software prototype for academic purposes, result of a master's research in PPGI/UNIRIO (Graduate Program on Informatics in the Federal University of State of Rio de Janeiro). Nevertheless, its functionalities already can support screenwriters in the task of converting business process models to narratives. **SYPApp** follow the MVC (Model-View-Controller) architecture.

## Download

### Mac, Windows and Linux

[Download the latest release](https://github.com/gpjc-unirio/syp-app/releases/latest)

## Implementation details

**SYPApp** is built using:

* [Java](https://java.com/), considering JAVA EE using JSF (JavaServer Pages) v2.2.
* [JavaServer Faces Technology](https://www.oracle.com/java/technologies/javaserverfaces.html) a Java technology for bulding server-side user interfaces to the web.
* [Prime Faces](https://www.primefaces.org/) version 8.0, for some of the components. 
* [Hibernate](https://hibernate.org/), for ORM framework that connects the data base to software features. 
* [Tomcat](https://tomcat.apache.org/download-90.cgi) v.9, apache server for Java web.
* [MySQL](https://www.mysql.com/) v. 5.7, for Data Base repositories. 
* [BPMN](https://www.omg.org/spec/BPMN/2.0/About-BPMN/), as business process language to convert into narrative elements. 
* [Camunda](https://github.com/camunda/camunda-modeler) v. 7.1.0 libray for manipulate BPMN files.


## SYPApp Development

To build the project:

* Install [MySQL](https://www.mysql.com/) if you don't already have it. After that, create a data base instance or schema (we recomend the name syp for te instance) and a user with root permission (create, update and delete the data base metadata and records)
* Install [Eclipse IDE](https://www.eclipse.org/downloads/packages/release/oxygen/3a/eclipse-ide-java-developers) to edit the Java code.
* Clone the repository
* Importe the source code into Eclipse
* In the file **"persistence.xml"** (inside of META-INF directory) update the user's information to access the data base. So, create (or uptade, in case of it exists) three tags:

```
<property name="javax.persistence.jdbc.url" value="" />
```
In the 'value' attribute you must put the server address. Ex.: jdbc:mysql://localhost/syp?useSSL=false (in caso of your data base schema was called by "syp")

```
<property name="javax.persistence.jdbc.user" value="" />
```
In the 'value' attribute you must put the data base user name.

```
<property name="javax.persistence.jdbc.password" value="" />
```
In the 'value' attribute you must put the data base user password.

* Server configuration: Before starting the Tomcat server, save the .war file into the "webapps" directory and run the server. After this, the software is ready to run. Case necessary, edit the BPMN file path (directory META-INF ''bpmn_directory.txt''. It is required to put the correct serve path information. If your Tomcat server runs applications inside of "webapps" folder, you do not need to do anything).

## SYPApp Help (In portuguese)

The [SYPApp's help](https://github.com/gpjc-unirio/syp-app/blob/main/docs/syp_help_v0.0.1.pdf) is in portuguese, the native language of the developers of this software.

## License

**SYPApp** are released under the MIT license.

### The MIT License (MIT)
Copyright (c) 2016.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
