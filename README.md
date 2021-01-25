# Music-Advisor
Integrated [Spotify's API](https://developer.spotify.com/documentation/web-api/) with design principles in Java to create a music advisor that makes preference-based suggestions, and shares new music and featured playlists. 
<br><br>
The idea for this project originated from JetBrains Academy, the online education portal of the JetBrains software company. For more information, [click here](https://hyperskill.org/projects/62).
## Usage
-  ```featured``` : a list of Spotify-featured playlists with their links fetched from API;
-  ```new``` : a list of new albums with artists and links on Spotify;
-  ```categories``` : a list of all available categories on Spotify (just their names);
-  ```playlists C_NAME```, where C_NAME is the name of category. The list contains playlists of this category and their links on Spotify;
-  ```exit``` shuts down the application.
## Program Files
### Main.java
```main``` takes 4 optional arguments, which must be specified in the following format: <br><br>
```{Authentication Server Path} -access {API Server Path} -resource``` wherein the placeholders represent IPv4/IPv6 addresses. <br><br>
In case no arguments are provided, the aforementioned paths are defaulted to "[https://accounts.spotify.com](https://accounts.spotify.com)" and "[https://api.spotify.com](https://api.spotify.com)".
### ConsoleUserInteraction.java
The crux of the user-program interaction; it is implemented as a Singleton and synchronized with **Main**. This class also implements the two methods declared in the **IUserInteraction.java** interface, namely: ```startInteraction()``` and ```executeService()```. The first method is called by **Main**, which, when invoked, forbids the user from proceeding further until the application has been authorized via the ```auth``` command. Upon confirmation of _OAuth_, the second method is called, wherein the user can request app services from the **Service*** class. 
### Service.java
This file contains the class that implements the **IService** interface. The methods ```getFeatured(); getNew(); getCategories(); getPlaylists()``` are defined here. Each of these methods follow a well-ordered structure: 
  - First, the ```createRequest()``` method of the **Interaction** singleton is invoked to generate the URI of the feature requested from the user.
  - Second, the _JsonParser_ gets the requested feature that is reminiscent of the method name as a _JsonArray_.
  - Finally, the corresponding method ```advise(JsonArray items)``` from the ```advisor``` instance of type **IAdvisor** is called. 
### IAdvisor.java
As mentioned above, this interface contains only one method signature, ```advise```. This method is implemented by the following classes:
  - AdviseAlbums
  - AdviseCategories
  - AdviseFeatures
  - AdvisePlaylists  
<br>_Note:_ Each of these methods take in the returned _JsonArray_ of _items_ from the API. The array is then iterated over to extract the relevant _JsonObjects_ and information from these objects is finally printed to the screen.
### Authorization.java
This file, as the name suggests, handles _[OAuth](https://www.youtube.com/watch?v=CPbvxxslDTU&ab_channel=InterSystemsLearningServices)_ - a protocol for authorization. It contains two predominately defined methods: ```createHttpServer()``` and ```authorizeRequest()```. The first method generates a URI as a hyperlink for the user to click and authorize the application. Consequently, the second method builds the Http request using the authenticated instance fields to retrieve the _access_token_. It is worth mentioning that both methods invoke methods on the **Interaction** class that primarily handles the http exchanges.    
### Interaction.java
All Http interactions are handled by this class, and therefore it is implemented as a Singleton to conform with the Java design patterns. This class contains three public methods which are invoked by multiple classes in the source code. The first method, ```serverInteraction()```, creates and binds an _HttpServer_. The server then fetches the authorization code via the Http handler by invoking the _createContext_ method. The second method, ```createRequest(String requestedURL)``` takes a URL and uses it to build a _HttpRequest_. This request is then sent by the client and the body of the response is finally returned by the method. The third and final method, ```handleRequest(HttpRequest request)```, takes an _HttpRequest_ as a parameter and fetches a _HttpResponse_. The body of the response is then inspected for the _access_token_ via the private ```parseAccessToken(String body)``` method. This method employs the **GSON** library made by Google; the inclusive JSON parser is then used to handle the JSON format returned by the Spotify API.
### Info.java
This file solely contains static instance fields that contain keys for the application to function in addition to snippets of partial links that help in building URIs.
### EOption.java
Contains the names of all app features, declared as enum constants.
