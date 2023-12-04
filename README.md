<h1 style="text-align: center">Pexel Repo</h1>

<br><br>

### Android app to download free Wallpapers & Videos

<br><br>

### How it works

This application depends on fetching data from www.pexels.com API.

<br>

![how it works](./repo_images/pexel_repo.drawio.png)

<br>

- It is written in Java, and sends the HTTP request and Getting response using OKHTTP3 dependency.
- The data comes in JSON format.
- I create Java parsers that converts JSON data into Java Objects that ease displaying the data into the GUI.
