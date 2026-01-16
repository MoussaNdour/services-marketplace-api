package com.example.marketplace.config;




import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.*;
import com.example.marketplace.repository.*;
import com.example.marketplace.entity.*;

@Component
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ProviderRepository providerRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ServiceRepository serviceRepository;
    @Autowired private ServiceProposalRepository serviceProposalRepository;
    @Autowired private AskingServiceRepository askingServiceRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            System.out.println("La base de données contient déjà des données. Seeding ignoré.");
            return;
        }

        Faker faker = new Faker(new Locale("fr"));
        Random random = new Random();

        System.out.println("🌱 Début du seeding de la base de données...");

        // 1. Créer des catégories
        List<Category> categories = createCategories();
        System.out.println("✅ " + categories.size() + " catégories créées");

        // 2. Créer des services
        List<Service> services = createServices(faker, categories);
        System.out.println("✅ " + services.size() + " services créés");

        // 3. Créer des clients
        List<Client> clients = createClients(faker, 30);
        System.out.println("✅ " + clients.size() + " clients créés");

        // 4. Créer des providers
        List<Provider> providers = createProviders(faker, 20);
        System.out.println("✅ " + providers.size() + " providers créés");

        // 5. Créer des admins
        List<Admin> admins = createAdmins(2);
        System.out.println("✅ " + admins.size() + " admins créés");

        // 6. Créer des propositions de services
        List<ServiceProposal> proposals = createServiceProposals(services, providers, random);
        System.out.println("✅ " + proposals.size() + " propositions de services créées");

        // 7. Créer des demandes de services
        List<AskingService> askingServices = createAskingServices(proposals, clients, random);
        System.out.println("✅ " + askingServices.size() + " demandes de services créées");


        System.out.println("🎉 Seeding terminé avec succès !");
    }

    private List<Category> createCategories() {
        String[] categoryNames = {
                "Plomberie", "Électricité", "Menuiserie", "Peinture",
                "Jardinage", "Nettoyage", "Déménagement", "Informatique",
                "Cours particuliers", "Réparation électroménager"
        };

        List<Category> categories = new ArrayList<>();
        for (String name : categoryNames) {
            Category category = new Category();
            category.setName(name);
            categories.add(categoryRepository.save(category));
        }
        return categories;
    }

    private List<Service> createServices(Faker faker, List<Category> categories) {
        Map<String, String[]> servicesByCategory = Map.of(
                "Plomberie", new String[]{"Réparation fuite", "Installation sanitaire", "Débouchage"},
                "Électricité", new String[]{"Installation électrique", "Dépannage", "Mise aux normes"},
                "Menuiserie", new String[]{"Pose de portes", "Fabrication meuble", "Réparation"},
                "Peinture", new String[]{"Peinture intérieure", "Peinture extérieure", "Décoration"},
                "Jardinage", new String[]{"Tonte pelouse", "Taille haies", "Aménagement jardin"},
                "Nettoyage", new String[]{"Ménage à domicile", "Nettoyage bureaux", "Vitrerie"},
                "Informatique", new String[]{"Dépannage PC", "Installation réseau", "Formation"}
        );

        List<Service> services = new ArrayList<>();
        for (Category category : categories) {
            String[] serviceNames = servicesByCategory.getOrDefault(
                    category.getName(),
                    new String[]{faker.commerce().productName()}
            );

            for (String name : serviceNames) {
                Service service = new Service();
                service.setName(name);
                service.setCategory(category);
                service.setCreatedAt(new Date());
                service.setMark(BigDecimal.valueOf(faker.number().randomDouble(2,1,5)));
                services.add(serviceRepository.save(service));
            }
        }
        return services;
    }

    private List<Client> createClients(Faker faker, int count) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(passwordEncoder.encode("password123"));
            user.setRole("CLIENT");
            user = userRepository.save(user);

            Client client = new Client();
            client.setUser(user);
            client.setFirstname(faker.name().firstName());
            client.setLastname(faker.name().lastName());
            client.setProfession(faker.job().title());
            clients.add(clientRepository.save(client));
        }
        return clients;
    }

    private List<Provider> createProviders(Faker faker, int count) {
        String[] professions = {
                "Plombier", "Électricien", "Menuisier", "Peintre",
                "Jardinier", "Agent d'entretien", "Déménageur", "Informaticien"
        };

        List<Provider> providers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(passwordEncoder.encode("password123"));
            user.setRole("PROVIDER");
            user = userRepository.save(user);

            Provider provider = new Provider();
            provider.setUser(user);
            provider.setFirstname(faker.name().firstName());
            provider.setLastname(faker.name().lastName());
            provider.setProfession(professions[faker.number().numberBetween(0, professions.length)]);

            // ✅ AJOUTE LA DESCRIPTION ICI
            provider.setDescription(faker.lorem().paragraph()); // Description réaliste
            // Ou plus court :
            // provider.setDescription(faker.lorem().sentence(15));

            providers.add(providerRepository.save(provider));
        }
        return providers;
    }

    private List<Admin> createAdmins(int count) {
        List<Admin> admins = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setEmail("admin" + (i + 1) + "@example.com");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRole("ADMIN");
            user = userRepository.save(user);

            Admin admin = new Admin();
            admin.setUser(user);
            admins.add(adminRepository.save(admin));
        }
        return admins;
    }

    private List<ServiceProposal> createServiceProposals(
            List<Service> services,
            List<Provider> providers,
            Random random) {

        List<ServiceProposal> proposals = new ArrayList<>();
        Set<String> uniquePairs = new HashSet<>();

        // Chaque provider propose 2-5 services
        for (Provider provider : providers) {
            int proposalCount = random.nextInt(4) + 2;
            for (int i = 0; i < proposalCount; i++) {
                Service service = services.get(random.nextInt(services.size()));
                String pair = service.getId() + "-" + provider.getId();

                if (!uniquePairs.contains(pair)) {
                    ServiceProposal proposal = new ServiceProposal();
                    proposal.setService(service);
                    proposal.setProvider(provider);
                    proposals.add(serviceProposalRepository.save(proposal));
                    uniquePairs.add(pair);
                }
            }
        }
        return proposals;
    }

    private List<AskingService> createAskingServices(
            List<ServiceProposal> proposals,
            List<Client> clients,
            Random random) {

        List<AskingService> askingServices = new ArrayList<>();

        // Chaque client demande 1-3 services
        for (Client client : clients) {
            int requestCount = random.nextInt(3) + 1;
            for (int i = 0; i < requestCount; i++) {
                ServiceProposal proposal = proposals.get(random.nextInt(proposals.size()));

                AskingService asking = new AskingService();
                asking.setProposal(proposal);
                asking.setClient(client);
                askingServices.add(askingServiceRepository.save(asking));
            }
        }
        return askingServices;
    }


}
