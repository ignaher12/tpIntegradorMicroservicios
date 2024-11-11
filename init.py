import asyncio
import subprocess

# Función para ejecutar un comando en un proceso hijo
async def run_command(command):
    # Se ejecuta el comando de forma asincrónica
    process = await asyncio.create_subprocess_shell(command,
                                                    stdout=asyncio.subprocess.PIPE,
                                                    stderr=asyncio.subprocess.PIPE)
    stdout, stderr = await process.communicate()  # Espera a que termine el proceso
    if process.returncode == 0:
        print(f"Comando '{command}' ejecutado con éxito")
        print(stdout.decode())
    else:
        print(f"Error al ejecutar '{command}': {stderr.decode()}")

# Función principal que ejecuta los comandos asincrónicamente
async def main():
    # Definimos los comandos a ejecutar
    commands = [
        "cd .\estudiantes",
        "mvn clean package -DskipTests",
        "mvn clean install -DskipTests",
        "cd ..",
        "cd .\inscripciones",
        "mvn clean package -DskipTests",
        "mvn clean install -DskipTests",
        "cd ..",
        "docker compose build",
        "docker compose up"
    ]
    
    # Crear tareas para ejecutar los comandos de forma asincrónica
    tasks = [run_command(command) for command in commands]
    
    # Ejecutar todas las tareas de forma asincrónica
    await asyncio.gather(*tasks)

# Ejecutar la función principal
if __name__ == "__main__":
    asyncio.run(main())
