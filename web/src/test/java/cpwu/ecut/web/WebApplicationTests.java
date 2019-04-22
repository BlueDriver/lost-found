package cpwu.ecut.web;

import cpwu.ecut.common.constant.enums.ApplyKindEnum;
import cpwu.ecut.common.utils.EnumUtils;
import cpwu.ecut.dao.entity.Comment;
import cpwu.ecut.dao.inter.CommentDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

    @Test
    public void contextLoads() throws Exception {

    }

    @Test
    public void test1() throws Exception {
        Integer i = EnumUtils.checkAndGetCode(6, ApplyKindEnum.values());
        System.out.println(i);
    }


    @Autowired
    private CommentDAO commentDAO;

    @Test
    public void test2() {
        // String[] ids = new String[]{"001", "002", "003", "004", "005", "006", "007"};
        String[] ids = new String[]{
                "006",
                "003",
                "002",
                "001",
                "007",
                "004",
                "005",
        };
        List<String> list = Arrays.asList(ids);


        Optional<Comment> commentOptional = commentDAO.findById("001");
        if (commentOptional.isPresent()) {
            System.out.println(commentOptional.get());
        }

        long s1 = System.currentTimeMillis();
        for (Object id : list) {
            Optional<Comment> optionalComment = commentDAO.findById((String) id);//单条查询
            System.out.println(optionalComment.get().getId());
        }
        long s2 = System.currentTimeMillis();
        System.err.println(s2 - s1);

        List<Comment> commentList = commentDAO.findAllById(list);//用in查询
        for (Comment comment : commentList) {
            System.out.println(comment.getId());
        }
        long s3 = System.currentTimeMillis();
        System.err.println(s3 - s2);

    }

    @Test
    public void test3() {
     /*   String[] ids = new String[]{
                "61b3046aec4942d0a68e5789200d8d41",
                "c0619603860b442ca282ba735ddc579d",
                "5545f5e568354351be784d43daac794d",
                "44be3e3484134f16ae7b4e69c115517b",
                "547d10d3b0364f75bbe41807e7c79427",
        };
        List<String> list = Arrays.asList(ids);

        List<String> idList = commentDAO.findCommentIdIn(list);
        System.out.println(idList.size());
        idList.forEach(i -> System.out.println(i));*/
    }

}
