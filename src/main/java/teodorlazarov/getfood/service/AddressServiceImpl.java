package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.Address;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.domain.models.service.AddressServiceModel;
import teodorlazarov.getfood.repository.AddressRepository;
import teodorlazarov.getfood.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public AddressServiceModel createAddress(AddressServiceModel addressServiceModel, String username) {
        Address address = this.modelMapper.map(addressServiceModel, Address.class);

        address = this.addressRepository.saveAndFlush(address);

        User user = this.userRepository.findUserByUsername(username).orElseThrow(() -> new IllegalArgumentException("Username not found!"));
        user.getAddresses().add(address);
        this.userRepository.saveAndFlush(user);

        return this.modelMapper.map(address, AddressServiceModel.class);
    }

    @Override
    public AddressServiceModel findAddressById(String id) {
        Address address = this.addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found!"));

        return this.modelMapper.map(address, AddressServiceModel.class);
    }

    @Override
    public AddressServiceModel updateAddress(AddressServiceModel newAddress, String username) {
        User user = this.userRepository.findUserByUsername(username).orElseThrow(() -> new IllegalArgumentException("Username not found!"));
        Address oldAddress = this.addressRepository.findById(newAddress.getId()).orElseThrow(()-> new IllegalArgumentException("Address not found!"));
//        List<String> userAddresses = user.getAddresses().stream().map(a -> a.getId()).collect(Collectors.toList());
//
//        if (!userAddresses.contains(newAddress.getId())){
//            throw new IllegalArgumentException("Address not found in your addresses!");
//        }

        if (!user.getAddresses().contains(oldAddress)){
            throw new IllegalArgumentException("Address not found in your addresses!");
        }

        oldAddress.setName(newAddress.getName());
        oldAddress.setCity(newAddress.getCity());
        oldAddress.setAddress(newAddress.getAddress());
        oldAddress.setPhoneNumber(newAddress.getPhoneNumber());
        oldAddress.setNotes(newAddress.getNotes());

        oldAddress = this.addressRepository.saveAndFlush(oldAddress);

        return this.modelMapper.map(oldAddress, AddressServiceModel.class);
    }

    @Override
    public void deleteAddress(String addressId, String username) {
        User user = this.userRepository.findUserByUsername(username).orElseThrow(() -> new IllegalArgumentException("Username not found!"));
        Address address = this.addressRepository.findById(addressId).orElseThrow(()-> new IllegalArgumentException("Address not found!"));

        if (!user.getAddresses().contains(address)){
            throw new IllegalArgumentException("Address not found in your addresses!");
        }

        user.getAddresses().remove(address);
        this.userRepository.saveAndFlush(user);

        this.addressRepository.delete(address);
    }

    @Override
    public boolean userHasAddress(String addressId, String username) {
        User user = this.userRepository.findUserByUsername(username).orElseThrow(() -> new IllegalArgumentException("Username not found!"));
        Address address = this.addressRepository.findById(addressId).orElseThrow(()-> new IllegalArgumentException("Address not found!"));

        return user.getAddresses().contains(address);
    }
}
