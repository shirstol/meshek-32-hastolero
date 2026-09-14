package il.meshek32.backend.api;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/images")
public class ImageController {
 @GetMapping("/{filename:.+}")
 public ResponseEntity<Resource> image(@PathVariable String filename) throws Exception {
  Path directory=Path.of("uploads").toAbsolutePath().normalize();
  Path file=directory.resolve(filename).normalize();
  if(!file.startsWith(directory)||!Files.isRegularFile(file)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"התמונה לא נמצאה");
  MediaType type=Files.probeContentType(file)!=null?MediaType.parseMediaType(Files.probeContentType(file)):MediaType.APPLICATION_OCTET_STREAM;
  return ResponseEntity.ok().contentType(type).body(new FileSystemResource(file));
 }
}
