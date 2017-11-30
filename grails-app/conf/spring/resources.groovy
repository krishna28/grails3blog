import com.sample.api.SecUserPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    secUserPasswordEncoderListener(SecUserPasswordEncoderListener, ref('mongoDatastore'))
    slugify(com.sample.api.Slugify)
}
