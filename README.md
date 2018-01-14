# Start your project without API 

Do you wanna start a project but you haven't API yet. Keep calm, you can use a offline flavor using local JSON files like a response, so when you have a API ready, you change to online flavor like this sample.

To test my code on your device/emulator, this is a suggest flow:

- Choice the online flavor on Android Studio
- Compile
- Try login
You will receive responde from web (mocky.io)

- Choice the offline flavor on Android Studio
- Compile
- Try login with any password
You will receive responde from json local file

- Try login with the password: 12345
You will receive a success message. The json is a static file, but was validated the route according the input

Good Study

p.s.: This is based on this project https://github.com/mirrajabi/okhttp-json-mock



