This project is intended to test the AppDynamics End User Monitoring (EUM) 
functionality with Wicket 1.3.x AJAX requests.

The project contains a simple Wicket application which executes an AJAX
request and pushes results onto the page. The application can pick up
the AppDynamics EUM header and footer through request attributes
(a.k.a. manual injection).

To run the project, simply build it through mvn (mvn package) and drop
the war file into your favorite container (tested on Tomcat 6).
