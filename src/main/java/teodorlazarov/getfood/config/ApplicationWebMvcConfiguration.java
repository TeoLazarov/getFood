package teodorlazarov.getfood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import teodorlazarov.getfood.web.interceptors.HiddenProductInterceptor;
import teodorlazarov.getfood.web.interceptors.TitleInterceptor;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {

    private final TitleInterceptor titleInterceptor;
    private final HiddenProductInterceptor hiddenProductInterceptor;

    @Autowired
    public ApplicationWebMvcConfiguration(TitleInterceptor titleInterceptor, HiddenProductInterceptor hiddenProductInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.hiddenProductInterceptor = hiddenProductInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.hiddenProductInterceptor).addPathPatterns("/cart/add/", "/orders/create");
    }
}
