#### 2. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 서버를 시작하면 localhost:8080으로 접근하면, 먼저 web.xml에 가서 welcome file로 무엇이 등록되어 있는지 본다. welcome file로 index.jsp가 등록되어 있다. index.jsp를 가서 보면, list.next로 redirect된다. 그런데 url이 *.next인 요청에 대해 처리하는 DispatcherServelet이다. 따라서 DispathcherServlet의 service()함수가 실행되게 되고, 여기에서 list.jsp를 보여주게 된다.

#### 3. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 

#### 10. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 

