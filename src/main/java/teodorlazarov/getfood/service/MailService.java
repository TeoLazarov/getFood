package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.OrderServiceModel;

public interface MailService {

    void sendEmail(OrderServiceModel orderServiceModel);
}
