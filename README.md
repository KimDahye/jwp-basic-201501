#### 2. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 서버를 시작하면 localhost:8080으로 접근하면, 먼저 web.xml에 가서 welcome file로 무엇이 등록되어 있는지 본다. welcome file로 index.jsp가 등록되어 있다. index.jsp를 가서 보면, list.next로 redirect된다. 그런데 url이 *.next인 요청에 대해 처리하는 DispatcherServelet이다. 따라서 DispathcherServlet의 service()함수가 실행되게 되고, 여기에서 list.jsp를 보여주게 된다.

#### 3. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 서버를 시작하면 localhost:8080으로 접근하면, 먼저 web.xml에 가서 welcome file로 무엇이 등록되어 있는지 본다. welcome file로 index.jsp가 등록되어 있다. index.jsp를 가서 보면, list.next로 redirect된다. 그런데 url이 *.next인 요청에 대해 처리하는 DispatcherServelet이다. 이 서블릿이 service()를 호출하기 전에 서블릿컨텍스트 리스너로 등록된 ContextLoaderListener의 contextInitialized()가 실행되게 된다. 이 함수 안에서 jwp.sql이 실행되어 mockup db를 만들어 주는 것 같다. 이후 DispathcherServlet의 service()함수가 실행되게 되고, 서비스 함수 안에서 model과 view가 결정된다. 이 후 JstlView 클래스의 render()함수에서 model안에 들어있는 질문 리스트를 서블릿 속성에 저장해둔다. 이후 list.jsp에 이전에 저정해둔 속성(저장해둔 질문목록들) 값들을 넣어 보여주게 된다.

#### 10. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 

