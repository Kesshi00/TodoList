package todolist.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todolist.lang.LangRepository;
import todolist.lang.LangServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static todolist.hello.HelloService.FALLBACK_LANG;

@WebServlet(name = "Todo", urlPatterns = {"/api/todos/*"})
public class TodoServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LangServlet.class);

    private TodoRepository repository;
    private ObjectMapper mapper;
    /**
     * Servlet container needs it.
     */
    @SuppressWarnings("unused")
    public TodoServlet(){
        this(new TodoRepository(), new ObjectMapper());
    }
    TodoServlet(TodoRepository repository, ObjectMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with parameters " + req.getParameterMap());
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), repository.findAll());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var pathInfo = req.getPathInfo();
        try{
            var todoId = Integer.valueOf(pathInfo.substring(1));
            var todo = repository.toogleTodo(todoId);
            resp.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(resp.getOutputStream(), todo);
        } catch (NumberFormatException e){
            logger.warn("Wrong path used: " + pathInfo);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var newTodo = mapper.readValue(req.getInputStream(), Todo.class);
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), repository.addTodo(newTodo));

    }
}
