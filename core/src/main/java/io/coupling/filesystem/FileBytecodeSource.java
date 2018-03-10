package io.coupling.filesystem;

import io.coupling.extract.BytecodeSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileBytecodeSource implements BytecodeSource {

  private Path path;

  private byte[] cachedBytecode;

  public FileBytecodeSource(Path filePath) {
    this.path = filePath;
  }

  @Override
  public byte[] bytecode() {
    try {
      cachedBytecode = Optional.ofNullable(cachedBytecode).orElse(Files.readAllBytes(path));
      return cachedBytecode;
    } catch (IOException exception) {
      throw new BytecodeNotFoundByPathException(path.toString(), exception);
    }
  }
}
