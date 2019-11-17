package com.honor.back.honorwebapp;

import Entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private HibernateSessionFactory() {}

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().addAnnotatedClass(Post.class)
                    .addAnnotatedClass(GalleryImage.class)
                    .addAnnotatedClass(GalleryComments.class)
                    .addAnnotatedClass(News.class)
                    .addAnnotatedClass(Actions.class)
                    .addAnnotatedClass(ActionsAlbum.class)
                    .addAnnotatedClass(ActionsImage.class)
                    .addAnnotatedClass(ActionsComments.class)
                    .addAnnotatedClass(ActionsType.class)
                    .addAnnotatedClass(Ordens.class)
                    .addAnnotatedClass(Veterans.class)
                    .addAnnotatedClass(GalleryAlbum.class).buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSession() {
//        if (sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
//                configuration.addAnnotatedClass(Post.class);
//                configuration.addAnnotatedClass(GalleryImage.class);
//                configuration.addAnnotatedClass(GalleryComments.class);
//                configuration.addAnnotatedClass(GalleryAlbum.class);
//                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//                sessionFactory = configuration.buildSessionFactory(builder.build());
//            } catch (Exception e) {
//                System.out.println("Исключение!" + e);
//            }
//        }
        return sessionFactory;
    }
//    private static SessionFactory buildSessionFactory() {
//        Configuration configuration = new Configuration().configure();
//        configuration.addAnnotatedClass(Post.class);
//        configuration.addAnnotatedClass(GalleryImage.class);
//        configuration.addAnnotatedClass(GalleryComments.class);
//        configuration.addAnnotatedClass(GalleryAlbum.class);
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
//        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
//
//        return sessionFactory;
//    }
//
//    public static SessionFactory getSession() {
//        if(sessionFactory == null) sessionFactory = buildSessionFactory();
//        return sessionFactory;
//    }
}
