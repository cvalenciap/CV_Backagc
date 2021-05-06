package pe.com.sedapal.agc.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;


import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;

@Service
public class FileStorageService {

	private Error error;
	private final Path fileStorageLocation;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	public Error getError() {
		return this.error;
	}

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			error = new Error(9002, Constantes.MESSAGE_ERROR.get(9002));
			logger.error("[AGC: FileStorageService - FileStorageService()] - "+ex.getMessage());
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				error = new Error(9003, StringUtils.replace(Constantes.MESSAGE_ERROR.get(9003), "{valor}", fileName));
			}
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			
			File source = convert(file);
	        File dest = new File(targetLocation.toString());	        
	        FileCopyUtils.copy(source, dest);
	        source.delete();
		} catch (IOException ex) {
			fileName = null;
			error = new Error(9004, StringUtils.replace(Constantes.MESSAGE_ERROR.get(9004), "{valor}", fileName),
					ex.getCause().toString());
			logger.error("[AGC: FileStorageService - storeFile()] - "+ex.getMessage());
		}
		return fileName;
	}
	
	public File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 	    
	    return convFile;
	}
}