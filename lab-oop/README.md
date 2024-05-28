# Description
Modelando o iPhone com UML: Funções de Músicas, Chamadas e Internet.

Cool! I just found out about [Mermaid](https://mermaid.js.org/).

I have zero experience with iPhones, so I used my "understanding" of Android devices to build the diagram.

# Hypothetical class diagram for smartphones
There is no mention of an application manager, and access to system resources is loosely commented.

```mermaid
---
title: Hypothetical Smartphone Diagram
---
classDiagram
  class SystemResource
  <<SystemAPIs>> SystemResource

  namespace MediaService {
    class MediaHandler
    class Player
    class AudioPlayer
    class VideoPlayer
  }
  <<interface>> MediaHandler
  MediaHandler ..> SystemResource : FileSystem
  class MediaHandler {
    -List<Format> supportedFormats

    -open(FilePath file) FileDescriptor
    -close(FileDescriptor file) void
    -save(ByteStream file, FilePath destination) Boolean
    -remove(FilePath file) Boolean
    -move(FilePath org, FilePath dst) Boolean
  }
  Player --|> MediaHandler
  Player ..> SystemResource : Screen/Speaker
  class Player {
    -Decoder decoder
    -EncodedStream stream

    -play() void
    -restart() void
    -pause() void
    -close() void
    -setSpeed(Integer speedLevel) void
    -stepFoward(Integer milliseconds) void
    -stepBackwards(Integer milliseconds) void
  }
  <<interface>> Player
  AudioPlayer ..|> Player
  VideoPlayer ..|> Player

  namespace InternetService {
    class Browser
  }
  Browser ..> SystemResource : Network/Wi-fi/Screen/GPS/FileSystem/Others
  class Browser {
    -List[Tabs] openTabs
    -FilePath tempStorage
    -FilePath cookieStorage
    -FilePath configFile

    -sendRequest(HttpRequest request) HttpResponse
    -buildRequest(UrlString url, HashMap headers, HashMap urlParams) HttpRequest
    -renderWebPage(HttpResonse response) void
    -newTab() void
    -setTab(Integer TabId) void
    -closeTab(Integer TabId) void
    -reloadTab(Integer TabId) void
    -close() void
    -requestPermission(SystemComponent component)
    -setCookie(WebCookie cookie)
    -getCookies(UrlString) List[WebCookie]
    -setConfigs(HashMap configs) Boolean
  }

  namespace PhoneService {
    class Contacts
    class Phone
    class SMS
  }
  Phone ..o Contacts
  SMS ..o Contacts
  Contacts ..> SystemResource : FileSystem/Screen
  Phone ..> SystemResource : Antenna/Microfone/Speaker/Screen
  SMS ..> SystemResource : Antenna/Screen/FileSystem

  class Phone {
    -List[SimCard] cards

    -call(String number, Integer simId) void
    -voicemail(String number, Integer simId) void
    -soundCallAlert() void
    -answer() void
    -setSimCard(Integer simId)
    -updateConnectionStatus()
  }

  class SMS {
    -List[SimCard] cards
    -FilePath messagesFolder

    -send(Message message, String number, Integer simId) void
    -delete(Integer messageId) void
    -markAsRead(Integer messageId) void
    -save(Message message)
  }
```
