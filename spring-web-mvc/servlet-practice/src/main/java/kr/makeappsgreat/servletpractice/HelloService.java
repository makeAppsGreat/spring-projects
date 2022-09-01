package kr.makeappsgreat.servletpractice;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getName() {
        return "makeappsgreat";
    }
}
