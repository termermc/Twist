# Twist
Twist is a small, lightwieght, and versitile webserver written in Java with the ability to serve multiple sites one the same IP address and webserver. It serves static HTML, with the ability to apply top and bottom template HTML for each webpage.

# How to use
To use, you simply download the Jarfile and run it using `java -jar twist.jar`. The appropriate files will be created, along with the descriptions. Once you have started it, terminate it to edit the configuration files.

# Configuring the Webserver
You may configure the behavior of the webserver by editing `twist.ini`.

The first field you may edit is called `ip`, and it contains the IP address to bind the server to. It comes prefilled with `127.0.0.1`, the default IP address for `localhost`.

The second field is called `port`, and it is used to specify the port to run the webserver on. It comes prefilled with port `2003`.

The third field is called `keystore`, and it is used to specify the path of a Java keystore. A Java keystore is a file that can contain an SSL certificate for enabling HTTPS. If left commented out, or pointing to an invalid path, HTTPS will not be enabled.

The fourth field is called `keystore-password`, and it is used to specify the password to unlock the keystore, if one was specified with `keystore`.

The fifth field is called `logging`, and it is used to enable or disable server logging. It comes prefilled with the value `true`.

# File Structure
If you were to visit the address the server was running on without doing anything to the configuration, you would be served with a 404 page. This is because there are no webpages for the domain you visited the server with. To create a filesystem for a domain, simply create a directory in `domains` with the name of the domain you want to serve. For example, to create a filesystem for `localhost`, you would create a directory named `localhost` in `domains`. To create an index for `localhost`, you would create an `index.html` inside of your newly created `localhost` directory. Now if you were to visit the site on your browser with localhost, you would be served the index file you created. To create a top template to be added to each webpage inside of your domain, simply create a `top.html` file inside of the root of your domain directory you created. The same can be done with `bottom.html`.

The visual structure: https://termer.net/twist_structure.png

Note: You'll have to create a domain directory for your server IP address if you want people to be able to connect to it independantly from the domain you use.

# Linking Domains
You can specify domains that use another domain's filesystem by editing `linkeddomains.ini`. To link a domain with a filesystem of another one, add a line to `linkeddomains.ini` with the following structure:

`domain-to-link > domain-with-a-filesystem`

# Compiling
To build, all you need is the source code and Sparkjava (http://sparkjava.com/download), the free and open source Java webserver. You maybe include it with maven by adding the following to your POM:

`<dependency>

    <groupId>com.sparkjava</groupId>
    
    <artifactId>spark-core</artifactId>
    
    <version>2.7.2</version>
    
</dependency>`
