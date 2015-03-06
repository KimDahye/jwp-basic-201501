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
* 컨트롤러 객체는 단 한번만 생성되어 Request Mapping시에 불려온다. 그리고 이 객체의 execute()함수만 각 스레드를 통해 여러번 실행되게 되는 것이다. 그런데 이전에는 객체의 field변수로 questionList나, question, answerList가 있었다. 멀티쓰레드 상황에서는 이와 같은 상황에 문제가 생길 수 있는데, 예를 들어 스레드A에서 questionList에 값을 저장해두었는데 다른 스레드에서 질문을 삭제한 뒤, 또 다른 스레드B에서 questionList값을 새로 저장해둘 수 있게 된다. 스레드 A는 스레드 B로 인해 변경된 questionList를 사용자에게 보여주게 된다. 물론 이때는 오히려 질문 삭제가 반영된 것이니 큰 문제가 아니라고 생각할 수도 있다. 하지만 answerList는 큰 문제가 발생할 수도 있다. 예를 들어, 스레드A에서 questionId 1에 대한 상세질문과 답변들을 보기 위해 questionId를 통해 상세 질문을 question에 저장한 다음에, 스레드B에서 questionId 2에 대한 상세질문을 question에 덮어 저장한 뒤, answerList에 값을 저장해두었다. 그 이후 다시 스레드A가 실행되어 answerList를 questionId1에 대한 답변들로 저장해둘 수 있다. 그렇다면 스레드 A에서는 클릭한 질문과는 다른 상세질문이 적혀있고, 스레드B에서는 클릭한 질문은 맞는데 답변이 질문과 맞지 않을 것이다. 따라서 이런 문제를 해결하기 위해서는 각 스레드마다 위의 변수들을 공유하지 않고 각자 갖고 있어야 한다. 따라서 각 변수들을 execute()함수 안으로 옮겨주면 된다.  
