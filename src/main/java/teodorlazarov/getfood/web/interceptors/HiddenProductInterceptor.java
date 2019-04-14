package teodorlazarov.getfood.web.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import teodorlazarov.getfood.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class HiddenProductInterceptor extends HandlerInterceptorAdapter {
    private final ProductService productService;

    @Autowired
    public HiddenProductInterceptor(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> a = request.getParameterMap();
        String[] productId = a.get("id");

        if (this.productService.isProductHidden(productId[0])){
            response.sendRedirect("/menu");
            return false;
        }

        return true;
    }
}
