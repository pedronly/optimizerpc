package com.optimizer;

import java.io.File;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao PC Optimizer!");
        ConfigLoader.loadConfig();
        cleanTemporaryFiles();
    }

    public static void cleanTemporaryFiles() {
    System.out.println("Iniciando limpeza de arquivos temporários...");
    
    List<String> tempFolders = (List<String>) ConfigLoader.getConfig("app.temporary-folders");
    boolean deleteSubfolders = (boolean) ConfigLoader.getConfig("cleaner.delete-subfolders");

    if (tempFolders == null || tempFolders.isEmpty()) {
        System.out.println("Nenhuma pasta temporária configurada para limpeza.");
        return;
    }

    for (String path : tempFolders) {
        if (path.contains("${TEMP}")) {
            path = path.replace("${TEMP}", System.getenv("TEMP"));
        }

        System.out.println("Limpando: " + path);
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            deleteFolderContents(folder, deleteSubfolders);
        } else {
            System.out.println("Pasta não encontrada: " + path);
        }
    }
}
   
    private static void deleteFolderContents(File folder, boolean deleteSubfolders) {
        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("Erro: Não foi possível listar os arquivos na pasta: " + folder.getAbsolutePath());
            return;
        }
    
        for (File file : files) {
            if (file.isDirectory() && deleteSubfolders) {
                deleteFolderContents(file, true); // Limpa subpastas recursivamente
            }
            try {
                if (file.delete()) {
                    System.out.println("Deletado: " + file.getAbsolutePath());
                } else {
                    System.out.println("Não foi possível deletar: " + file.getAbsolutePath());
                }
            } catch (Exception e) {
                System.err.println("Erro ao deletar " + file.getAbsolutePath() + ": " + e.getMessage());
            }
        }
    }
    
}
