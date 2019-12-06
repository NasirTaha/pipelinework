package com.kostal.pipelineworks

class Notifier{
    static String sendToEmail(String message = "Empty message"){

        print("\n ***** Override: inside sendToEmail ***** ${message}\n \n \n ")
        return "Override: Send to email: "+ message

    }
    static String sendToLogServer(String message = "Empty message"){

        print("\n ***** Override: inside sendToLogServer *****  ${message}\n \n \n ")
        return "Override: Send to log serevr: "+ message
        
    }
}