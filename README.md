#### 2. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
<ol>
<li>컨테이너를 시작하면, 먼저 DD를 살펴봅니다. welcome file로 어떤 페이지가 등록되어 있는지, servlet config의 parameter로 설정해둔 것이 있는지, servlet context의 parameter로 설정해둔 것이 있는지 살펴봅니다. 또, servlet context listener로 설정해둔 클래스가 있는지 살펴봅니다. </li>
<li>주어진 문제에서는 DD에 welcome file로 index.jsp가, servlet context listener로 next.support.context.ContextLoaderListener가 등록되어 있습니다. </li>
<li>컨테이너는 welcome file과 servlet context listener를 잘 기억해둡니다.</li>
<li>이 애플리케이션에서 쓸 context 객체를 만듭니다.</li>
<li>이 때 context 객체가 초기화되는 이벤트를 context listener가 알아차리게 되어 contextInitialized() 함수를 실행합니다. 여기에서 jwp.sql문을 따라 mockup DB 설정이 됩니다. </li>
<li> 컨테이너는 서블릿 클래스가 있는 폴더에서 서블릿 파일을 검색하여 찾습니다.</li>
<li> 찾은 서블릿의 생성자를 통해 서블릿 객체를 만듭니다.  </li>
<li> 7)까지는 서블릿의 로딩이 완료된 것이라고 할 수 있고, 서블릿 초기화가 완료되는 것은 init()함수 호출이 완료되고 나서입니다. 하지만, 컨테이너에 따라 init() 함수 호출을 애플리케이션 배포시에 하는 경우도 있고, client로 부터 첫 요청이 있을 때 하는 경우도 있습니다. 따라서 톰캣이 어떤 전략을 따르는 컨테이너인가에 따라 init()함수를 호출할 수도 있고 아닐 수도 있습니다. (4번 문제를 보니 톰캣은 두번째 전략을 따르는 것 같습니다.)</li>
<li> DD 혹은 annotation을 통해 서블릿 클래스와 url을 맵핑합니다.</li>
</ol>

#### 3. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* localhost:8080으로 접근하면, url에 파일 이름까지 없으므로 welcome file로 등록된 파일을 보내줍니다. 
* welcome file로 index.jsp가 등록되어 있으니 index.jsp를 보내줍니다.
* index.jsp를 가서 보면, list.next로 redirect됩니다. 
* 따라서 클라이언트에서는 url이 list.next로바뀌어 서버에 재요청을 하게 됩니다.
* url이 "*.next"인 요청에 대해 처리하는 servlet이 DispatcherServelet이므로 (이것은  annotation으로 등록되어 있다.) 이 서블릿의  service()함수가 실행됩니다. (이 전에 이 요청이 해당 서블릿의 처음 요청이었다면 init()함수가 실행되게 됩니다.)
* service() 함수 안에서 요청받은  uri를 분석해 해당 controller 객체를 map에서 가져옵니다. uri가 list.next이었으므로 34줄 rm.findController(urlExceptParameter(req.getRequestURI()))에서는 ListController 객체가 반환됩니다.
* 37줄 controller.execute(req, resp);에서 ListController의 execute()함수가 실행되므로, view에는 list.jsp가 model에는 질문목록이 저장됩니다. 
* 39줄dptj JstlView 클래스의 render()함수가 실행된다. model안에 들어있는 질문 리스트를 리퀘스트 속성에 저장해두고, list.jsp로 포워딩 합니다.

#### 10. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 

