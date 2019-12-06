#!/usr/bin/env groovy

def call(String name, Closure body) {
        echo "before override stage"  
        
        def project_name = env.JOB_NAME
                
        sendToPost(project_name, name, "start")

        timestamps {
            return steps.stage("$name"){
                body()
            }
        }
        echo  'after override stage'  
        sendToPost(project_name, name, "end")

}

def sendToPost(String project_name, String stage_name, String flag ){
    // POST
    try{
        //Get current build timestamp
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
        def now = new Date()
        def build_timestamp = now.format("yyyy-MM-dd HH:mm:ss.SSS")

        def url = "http://localhost:1000/jobinfo/"
        def post = new URL(url).openConnection();
        def message = """
        {"project_name": "$project_name",
            "stage_name": "$stage_name",
            "flag": "$flag",
            "build_id": "$env.BUILD_NUMBER",
            "node_name": "$env.NODE_NAME",
            "timestamp": "$build_timestamp"}
        """
        post.setRequestMethod("POST")
        post.setDoOutput(true)
        post.setRequestProperty("Content-Type", "application/json")
        post.getOutputStream().write(message.getBytes("UTF-8"));
        def postRC = post.getResponseCode();
        
        if(postRC.equals(200)) {
            println(post.getInputStream().getText());
        }
        else{
            println("   *********   fail   ************ ")
            println("Rest API response code: ${postRC}")
            println(message);
            //TODO: get useful reponse message from rest api
        }
    }
    catch(Exception ex){
        //TODO:
        println (ex.toString())
    }
    finally{
        //TODO: 
    }
}
