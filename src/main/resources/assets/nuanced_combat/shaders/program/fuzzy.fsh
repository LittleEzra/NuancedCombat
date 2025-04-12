#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

// Taken from Sebastian Lague's ray tracing video: https://www.youtube.com/watch?v=Qz0KTGYJtUk
float hash(inout uint state){
    state *= (state + 195439) * (state + 124395) * (state + 845921);
    return state / 4294967295.0;
}

void main() {
    uint pixelIndex = uint((texCoord.x + texCoord.y) * 1920);
    hash(pixelIndex);

    vec4 diffuseColor = texture(DiffuseSampler, texCoord + vec2(pixelIndex * 0.005, pixelIndex * 0.005));

    fragColor = vec4(diffuseColor.rgb, 1.0);
}
