package todolist.todo;

import todolist.HibernateUtil;
import todolist.lang.Lang;

import java.util.List;

public class TodoRepository {
    List<Todo> findAll(){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        var result = session.createQuery("from Todo", Todo.class).list();

        transaction.commit();
        session.close();
        return result;
    }

}
