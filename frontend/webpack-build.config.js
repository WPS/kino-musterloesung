const {CycloneDxWebpackPlugin} = require('@cyclonedx/webpack-plugin')

module.exports = {
  module: {
    rules: [
      {
        test: /\.css$/,
        use: [
          'postcss-loader'
        ]
      }
    ]
  },
  plugins: [
    new CycloneDxWebpackPlugin({
      specVersion: '1.6',
      outputLocation: './.bom',
      reproducibleResults: true,
      validateResults: true
    })
  ]
}
