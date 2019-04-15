package teodorlazarov.getfood.web.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import teodorlazarov.getfood.domain.entities.OrderItem;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.repository.UserRepository;
import teodorlazarov.getfood.service.ProductService;
import teodorlazarov.getfood.service.ShoppingCartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Component
public class HiddenProductInterceptor extends HandlerInterceptorAdapter {

    private final ProductService productService;
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public HiddenProductInterceptor(ProductService productService, UserRepository userRepository, ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Principal principal = request.getUserPrincipal();
        User user = this.userRepository.findUserByUsername(principal.getName()).orElseThrow();
        List<OrderItem> shoppingCartItems = user.getShoppingCart().getItems();
        boolean hasHiddenProducts = false;

        if (parameterMap.get("id") != null){
            String[] productId = parameterMap.get("id");
            String id = productId[0];
            if (id != null && this.productService.isProductHidden(id)){
                hasHiddenProducts = true;
            }
        }

        for (OrderItem shoppingCartItem : shoppingCartItems) {
            if (shoppingCartItem.getProduct().isHidden()){
                hasHiddenProducts = true;
                this.shoppingCartService.removeOrderItem(shoppingCartItem.getId(), user.getShoppingCart().getId());
            }
        }

        if (hasHiddenProducts){
            response.sendRedirect("/cart");
            return false;
        }

        return true;
    }
}
